package com.avalon.core.subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.avalon.core.AkkaServerManager;
import com.avalon.core.AvalonEngine;
import com.avalon.core.ContextResolver;
import com.avalon.core.cluster.ClusterListener;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.ServerSupervisorMessage;
import com.avalon.core.message.ServerSupervisorMessage.ServerOnline;
import com.avalon.core.message.TransportSupervisorMessage;
import com.avalon.core.supervision.ConnectionSessionSupervisor;
import com.avalon.setting.AvalonServerMode;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import akka.cluster.Member;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import jodd.util.MathUtil;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * 服务器状态监听
 * 
 * @author zero
 *
 */
public class ServerSupervisorSubscriber extends UntypedActor {

	private static final int DEFAULT_SERVRE_ID = -1;

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

	private int serverId;
	/**
	 * 其他集群内的机器
	 */
	public static List<MemberWaper> members = new ArrayList<MemberWaper>();

	public ServerSupervisorSubscriber() {
		mediator = DistributedPubSub.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(ClusterListener.shardName, getSelf()), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		PropertiesWrapper propertiesWrapper = ContextResolver.getPropertiesWrapper();
		if (msg instanceof ServerSupervisorMessage.ServerOnline) {
			ServerOnline serverOnline = (ServerSupervisorMessage.ServerOnline) msg;
			// 检查是否是自己这台机器，如果是则不加入其他机器列表
			if (serverOnline.UUID.equals(ClusterListener.GEUID)) {
				log.info("msg is ServerSupervisorMessage.ServerOnline: is then same");
				return;
			} else {
				for (MemberWaper member : members) {
					if (serverOnline.addressUid == member.uid) {
						log.info("msg is ServerSupervisorMessage.ServerOnline:it has same" + member.uid);
						return;
					}
				}
				log.info("add new Server cool");
				MemberWaper memberWaper = new MemberWaper(serverOnline.serverId, serverOnline.addressUid,
						AvalonServerMode.getSeverMode(serverOnline.type), serverOnline.addressPath);
				members.add(memberWaper);

				// 检查自己是否已经获得自己机器的节点信息，并检查这个消息是否需要回复
				if (ServerSupervisorSubscriber.member != null && !serverOnline.noBack) {
					log.debug("msg is ServerSupervisorMessage.ServerOnline:send back message");
					int ServerId;
					if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
						ServerId = propertiesWrapper.getIntProperty(SystemEnvironment.GATE_BINDING, DEFAULT_SERVRE_ID);
					} else {
						ServerId = propertiesWrapper.getIntProperty(SystemEnvironment.APP_ID, DEFAULT_SERVRE_ID);
					}
					ServerSupervisorMessage supervisorMessage = new ServerSupervisorMessage.ServerOnline(
							ClusterListener.GEUID, avalonServerMode.type,
							ServerSupervisorSubscriber.member.address().toString(),
							ServerSupervisorSubscriber.member.uniqueAddress().uid(), ServerId, true);
					getSender().tell(supervisorMessage, self());
				}
			}
		}
		// 检查是不是同一个节点，并赋予自己节点信息
		else if (msg instanceof ServerSupervisorMessage.ServerIsTheSame) {
			if (((ServerSupervisorMessage.ServerIsTheSame) msg).UUID.equals(ClusterListener.GEUID)) {
				log.debug("msg is ServerSupervisorMessage.ServerIsTheSame find self");
				ServerSupervisorSubscriber.member = ((ServerSupervisorMessage.ServerIsTheSame) msg).member;
				this.avalonServerMode = AvalonServerMode
						.getSeverMode(((ServerSupervisorMessage.ServerIsTheSame) msg).type);
				AvalonServerMode serverMode = AvalonEngine.mode;

				if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
					serverId = propertiesWrapper.getIntProperty(SystemEnvironment.GATE_BINDING, DEFAULT_SERVRE_ID);
				} else {
					serverId = propertiesWrapper.getIntProperty(SystemEnvironment.APP_ID, DEFAULT_SERVRE_ID);
				}
				String addressStr = member.address().toString();
				int addressUid = member.uniqueAddress().uid();

				ServerSupervisorMessage supervisorMessage = new ServerSupervisorMessage.ServerOnline(
						ClusterListener.GEUID, serverMode.type, addressStr, addressUid, serverId);
				mediator.tell(new DistributedPubSubMediator.Publish(ClusterListener.shardName, supervisorMessage),
						getSelf());

				ActorSystem actorSystem = AkkaServerManager.getInstance().getActorSystem();
				Scheduler scheduler = actorSystem.scheduler();
				FiniteDuration delayTime = Duration.create(60, TimeUnit.SECONDS);
				FiniteDuration periodTime = Duration.create(60, TimeUnit.SECONDS);
				scheduler.schedule(delayTime, periodTime, new Runnable() {
					@Override
					public void run() {
						ServerSupervisorMessage supervisorMessage = new ServerSupervisorMessage.Ping(
								ClusterListener.GEUID, addressUid, serverMode.type, serverId);
						mediator.tell(
								new DistributedPubSubMediator.Publish(ClusterListener.shardName, supervisorMessage),
								getSelf());
					}
				}, actorSystem.dispatcher());
			}
		}

		// 失去一个节点
		else if (msg instanceof ServerSupervisorMessage.ServerLost) {
			for (MemberWaper member : members) {
				if (member.uid == ((ServerSupervisorMessage.ServerLost) msg).memberUid) {
					members.remove(member);
					lostServer(member.serverId);
					return;
				}
			}
		}
		// 发送重定向信息
		else if (msg instanceof ServerSupervisorMessage.SendRedirectMessage) {
			ServerSupervisorMessage reciveRedirectMessage = new ServerSupervisorMessage.ReciveRedirectMessage(
					((ServerSupervisorMessage.SendRedirectMessage) msg).sender,
					((ServerSupervisorMessage.SendRedirectMessage) msg).path,
					((ServerSupervisorMessage.SendRedirectMessage) msg).message);
			DistributedPubSubMediator.Publish publish = new DistributedPubSubMediator.Publish(ClusterListener.shardName,
					reciveRedirectMessage);
			mediator.tell(publish, getSelf());
		}
		// 收到重定向信息，转发
		else if (msg instanceof ServerSupervisorMessage.ReciveRedirectMessage) {
			String path = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).path;
			ActorSelection actorSelection = getContext().actorSelection(path);
			Object message = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).message;
			ActorRef sender = ((ServerSupervisorMessage.ReciveRedirectMessage) msg).sender;

			actorSelection.tell(message, sender);
		}
		// 收到重定向信息，转发
		else if (msg instanceof ServerSupervisorMessage.Ping) {
			String uuid = ((ServerSupervisorMessage.Ping) msg).UUID;
			int addressUid = ((ServerSupervisorMessage.Ping) msg).addressUid;
			int type = ((ServerSupervisorMessage.Ping) msg).type;
			int serverId2 = ((ServerSupervisorMessage.Ping) msg).serverId;
			if (ClusterListener.GEUID.equals(uuid)) {
				log.info("msg is ServerSupervisorMessage.Ping find self");
				return;
			}
			boolean getPing = false;
			for (MemberWaper memberWaper : members) {
				if (memberWaper.uid == addressUid) {
					getPing = true;
					memberWaper.lastPingTime = System.currentTimeMillis();
					log.info("msg is ServerSupervisorMessage.Pinging");
				}
			}
			if (!getPing) {
				MemberWaper memberWaper = new MemberWaper(serverId2, addressUid, AvalonServerMode.getSeverMode(type),
						getSender().path().address().toString());
				members.add(memberWaper);
				log.info("add new server by ping");
			}
			log.info("get ping actor " + getSender().path().address().toString());
		}
		/**
		 * 收到从网关Server的节点绑定信息，根据现在拥有的游戏服务器进行分发
		 */
		else if (msg instanceof ServerSupervisorMessage.DistributionConnectionSessionsProtocol) {
			int serverid = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).serverid;
			ActorRef sender = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).sender;
			Object origins = ((ServerSupervisorMessage.DistributionConnectionSessionsProtocol) msg).origins;

			CluserSessionMessage message = new CluserSessionMessage(sender, origins);
			List<MemberWaper> list = getGameServer();
			/**
			 * 如果serverid<0情况，则随机分配到不同的游戏逻辑服务器
			 */
			if (serverid < 0) {
				int index = MathUtil.randomInt(0, list.size());
				MemberWaper memberWaper = list.get(index);
				String string = memberWaper.toString();
				String fixPath = string + SystemEnvironment.AKKA_USER_PATH + ConnectionSessionSupervisor.IDENTIFY;

				ActorSelection actorSelection = getContext().actorSelection(fixPath);
				actorSelection.tell(message, getSelf());

			} else {
				for (MemberWaper memberWaper : list) {
					if (memberWaper.serverId == serverid) {
						String stringAddress = memberWaper.address;
						String fixPath = stringAddress + SystemEnvironment.AKKA_USER_PATH
								+ ConnectionSessionSupervisor.IDENTIFY;
						ActorSelection actorSelection = getContext().actorSelection(fixPath);
						actorSelection.tell(message, getSelf());
					}
				}
			}

		}

		else if (msg instanceof DistributedPubSubMediator.SubscribeAck) {
			log.debug("subscribing");
		} else {
			unhandled(msg);
		}
	}

	private void lostServer(int serverId) {
		TransportSupervisorMessage message=new TransportSupervisorMessage.ServerLost(serverId);
		AkkaServerManager instance = AkkaServerManager.getInstance();
		instance.getTransportSupervisorRef().tell(message, getSelf());
	}

	private List<MemberWaper> getGameServer() {
		List<MemberWaper> result = new ArrayList<>();
		for (MemberWaper memberWaper : members) {
			if (!memberWaper.mode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
				result.add(memberWaper);
			}
		}
		return result;
	}

}

class MemberWaper {
	public final int serverId;
	public final int uid;
	public final AvalonServerMode mode;
	public final String address;
	public long lastPingTime;

	public MemberWaper(int serverId, int uid, AvalonServerMode mode, String address) {
		super();
		this.serverId = serverId;
		this.uid = uid;
		this.mode = mode;
		this.address = address;
		this.lastPingTime = System.currentTimeMillis();
	}

}