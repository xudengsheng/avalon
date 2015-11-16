package com.avalon.core.subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.avalon.core.AvalonEngine;
import com.avalon.core.ContextResolver;
import com.avalon.core.cluster.ClusterListener;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.ServerSupervisorMessage;
import com.avalon.core.message.ServerSupervisorMessage.ServerOnline;
import com.avalon.core.supervision.ConnectionSessionSupervisor;
import com.avalon.setting.AvalonServerMode;
import com.avalon.setting.SystemEnvironment;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.actor.UntypedActor;
import akka.cluster.Member;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import jodd.util.MathUtil;

/**
 * 服务器状态监听
 * 
 * @author zero
 *
 */
public class ServerSupervisorSubscriber extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static final String IDENTIFY = "ServerSupervisorSubscriber";
	/**
	 * 本机的远程地址信息
	 */
	public static Member member;
	/**
	 * 当前服务的启动模式
	 */
	private AvalonServerMode avalonServerMode;
	/**
	 * 广播使用的
	 */
	ActorRef mediator;
	/**
	 * 其他集群内的机器
	 */
	public static Map<AvalonServerMode, List<MemberWaper>> members = new HashMap<AvalonServerMode, List<MemberWaper>>();
	

	public ServerSupervisorSubscriber() {
		mediator = DistributedPubSub.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(ClusterListener.shardName, getSelf()), getSelf());
		AvalonServerMode[] values = AvalonServerMode.values();
		for (AvalonServerMode avalonServerMode : values) {
			members.put(avalonServerMode, new ArrayList<MemberWaper>());
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ServerSupervisorMessage.ServerOnline) {
			ServerOnline serverOnline = (ServerSupervisorMessage.ServerOnline) msg;
			// 检查是否是自己这台机器，如果是则不加入其他机器列表
			if (serverOnline.UUID.equals(ClusterListener.GEUID)) {
				log.debug("msg is ServerSupervisorMessage.ServerOnline: is then same");
				return;
			} else {
				List<MemberWaper> list = members.get(AvalonServerMode.getSeverMode(serverOnline.type));
				for (MemberWaper member : list) {
					if (serverOnline.member.uniqueAddress().uid() == member.member.uniqueAddress().uid()) {
						log.debug("msg is ServerSupervisorMessage.ServerOnline:it has same"	+ member.member.uniqueAddress().uid());
						return;
					}
				}
				log.debug("add new Server cool");
				MemberWaper memberWaper = new MemberWaper(serverOnline.serverId,AvalonServerMode.getSeverMode(serverOnline.type), serverOnline.member);
				list.add(memberWaper);
				
				
				// 检查自己是否已经获得自己机器的节点信息，并检查这个消息是否需要回复
				if (ServerSupervisorSubscriber.member != null && !serverOnline.noBack) {
					log.debug("msg is ServerSupervisorMessage.ServerOnline:send back message");
					int ServerId;
					if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
						ServerId = ContextResolver.getPropertiesWrapper().getIntProperty(SystemEnvironment.GATE_BINDING,-1);
					} else {
						ServerId = ContextResolver.getPropertiesWrapper().getIntProperty(SystemEnvironment.APP_ID, -1);
					}
					ServerSupervisorMessage supervisorMessage = new ServerSupervisorMessage.ServerOnline(ClusterListener.GEUID, avalonServerMode.type, ServerSupervisorSubscriber.member, ServerId,true);
					getSender().tell(supervisorMessage, self());
				}
			}
		}
		// 检查是不是同一个节点，并赋予自己节点信息
		else if (msg instanceof ServerSupervisorMessage.ServerIsTheSame) {
			if (((ServerSupervisorMessage.ServerIsTheSame) msg).UUID.equals(ClusterListener.GEUID)) {
				log.debug("msg is ServerSupervisorMessage.ServerIsTheSame find self");
				ServerSupervisorSubscriber.member = ((ServerSupervisorMessage.ServerIsTheSame) msg).member;
				this.avalonServerMode = AvalonServerMode.getSeverMode(((ServerSupervisorMessage.ServerIsTheSame) msg).type);
				AvalonServerMode serverMode = AvalonEngine.mode;
				int ServerId;
				if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
					ServerId = ContextResolver.getPropertiesWrapper().getIntProperty(SystemEnvironment.GATE_BINDING,-1);
				} else {
					ServerId = ContextResolver.getPropertiesWrapper().getIntProperty(SystemEnvironment.APP_ID, -1);
				}
				ServerSupervisorMessage supervisorMessage = new ServerSupervisorMessage.ServerOnline(ClusterListener.GEUID, serverMode.type, member, ServerId);
				mediator.tell(new DistributedPubSubMediator.Publish(ClusterListener.shardName, supervisorMessage),getSelf());
				
			}
		}
		
		// 失去一个节点
		else if (msg instanceof ServerSupervisorMessage.ServerLost) {
			for (Entry<AvalonServerMode, List<MemberWaper>> entry : members.entrySet()) {
				for (MemberWaper member : entry.getValue()) {
					if (member.member.equals(((ServerSupervisorMessage.ServerLost) msg).Member)) {
						entry.getValue().remove(member);
						return;
					}
				}
			}
		}
		// 发送重定向信息
		else if (msg instanceof ServerSupervisorMessage.SendRedirectMessage) {
			ServerSupervisorMessage reciveRedirectMessage = new ServerSupervisorMessage.ReciveRedirectMessage(
					((ServerSupervisorMessage.SendRedirectMessage) msg).sender,
					((ServerSupervisorMessage.SendRedirectMessage) msg).path,
					((ServerSupervisorMessage.SendRedirectMessage) msg).message);
			DistributedPubSubMediator.Publish publish = new DistributedPubSubMediator.Publish(ClusterListener.shardName, reciveRedirectMessage);
			mediator.tell(publish,getSelf());
		}
		// 收到重定向信息，转发
		else if (msg instanceof ServerSupervisorMessage.ReciveRedirectMessage) {
			String path = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).path;
			ActorSelection actorSelection = getContext().actorSelection(path);
			Object message = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).message;
			ActorRef sender = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).sender;
			
			actorSelection.tell(message,sender);
		}
		/**
		 * 收到从网关Server的节点绑定信息，根据现在拥有的游戏服务器进行分发
		 */
		else if (msg instanceof ServerSupervisorMessage.DistributionConnectionSessionsProtocol) {
			int serverid = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).serverid;
			ActorRef sender = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).sender;
			Object origins = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).origins;

			CluserSessionMessage message = new CluserSessionMessage(sender, origins);
			List<MemberWaper> list = members.get(AvalonServerMode.SERVER_TYPE_GAME);

			
			/**
			 * 如果serverid<0情况，则随机分配到不同的游戏逻辑服务器
			 */
			if (serverid < 0) {
				int index = MathUtil.randomInt(0, list.size());
				MemberWaper memberWaper = list.get(index);
				Address address = memberWaper.member.address();
				String string = address.toString();
				String fixPath = string + SystemEnvironment.AKKA_USER_PATH + ConnectionSessionSupervisor.IDENTIFY;
				
				ActorSelection actorSelection = getContext().actorSelection(fixPath);
				actorSelection.tell(message, getSelf());

			} else {
				for (MemberWaper memberWaper : list) {
					if (memberWaper.serverId == serverid) {
						Address address = memberWaper.member.address();
						String string = address.toString();
						ActorSelection actorSelection = getContext().actorSelection(
								string + SystemEnvironment.AKKA_USER_PATH + ConnectionSessionSupervisor.IDENTIFY);
						actorSelection.tell(message, getSelf());
					}
				}
			}

		}

		else if (msg instanceof DistributedPubSubMediator.SubscribeAck) {
			log.info("subscribing");
		} else {
			unhandled(msg);
		}
	}

}

class MemberWaper {
	public final int serverId;
	public final AvalonServerMode mode;
	public final Member member;

	public MemberWaper(int serverId, AvalonServerMode mode, Member member) {
		super();
		this.serverId = serverId;
		this.mode = mode;
		this.member = member;
	}

}