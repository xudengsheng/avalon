package com.avalon.core.subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.core.AvalonEngine;
import com.avalon.core.ContextResolver;
import com.avalon.core.cluster.ClusterListener;
import com.avalon.core.message.AvaloneMessage;
import com.avalon.core.message.CluserSessionMessage;
import com.avalon.core.message.DistributionConnectionSessionsProtocol;
import com.avalon.core.message.MessageType;
import com.avalon.core.message.NetServerLost;
import com.avalon.core.message.Ping;
import com.avalon.core.message.ReciveRedirectMessage;
import com.avalon.core.message.SendRedirectMessage;
import com.avalon.core.message.ServerIsTheSame;
import com.avalon.core.message.ServerLost;
import com.avalon.core.message.ServerOnline;
import com.avalon.core.supervision.ConnectionSessionSupervisor;
import com.avalon.setting.AvalonServerMode;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.AkkaDecorate;
import com.avalon.util.AkkaPathDecorate;
import com.avalon.util.PropertiesWrapper;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import akka.cluster.Member;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import jodd.util.MathUtil;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * 服务器状态监听 添加其他节点服务器
 * 
 * @author zero
 *
 */
public class ServerSupervisorSubscriber extends UntypedActor
{

	private static final int DEFAULT_SERVRE_ID = -1;

	private static Logger logger = LoggerFactory.getLogger("ServerSupervisorSubscriber");

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

	Cancellable pingSchedule;

	private int serverId;
	/**
	 * 其他集群内的机器
	 */
	public static List<MemberWaper> members = new ArrayList<MemberWaper>();

	public ServerSupervisorSubscriber()
	{
		logger.debug("ServerSupervisorSubscriber create");
		mediator = DistributedPubSub.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(ClusterListener.shardName, getSelf()), getSelf());
	}

	private void addMemberWaper(MemberWaper memberWaper)
	{
		members.add(memberWaper);
		if (avalonServerMode.hasAppListener && memberWaper.serverId != serverId)
		{
			System.out.println(memberWaper.serverId + "====" + serverId);
			ContextResolver.getAppListener().nodeOnline(memberWaper.serverId, memberWaper.address);
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception
	{
		PropertiesWrapper propertiesWrapper = ContextResolver.getPropertiesWrapper();
		if (msg instanceof AvaloneMessage)
		{
			switch (((AvaloneMessage) msg).getMessageType())
			{
				case ServerIsTheSame :
					checkIsSameNodeAndCreatePing((ServerIsTheSame) msg, propertiesWrapper);
					break;
				case ServerOnline :
					processServerOnline(msg, propertiesWrapper);
					break;
				case ServerLost :
					process(msg);
					break;
				case SendRedirectMessage :
					processSendRedirectMessage(msg);
					break;
				case ReciveRedirectMessage :
					processReciveRedirectMessage(msg);
					break;
				case DistributionConnectionSessionsProtocol:
					processDistributionConnectionSessionsProtocol(msg);
					break;
				case Ping:
					processPing(msg);
					break;
				default :
					break;
			}
		}

		/**
		 * 收到从网关Server的节点绑定信息，根据现在拥有的游戏服务器进行分发
		 */

		else if (msg instanceof DistributedPubSubMediator.SubscribeAck)
		{
			logger.debug("subscribing ack");
		}
		else
		{
			unhandled(msg);
		}
	}

	private void processPing(Object msg)
	{
		logger.debug("msg is ServerSupervisorMessage.Ping");
		String uuid = ((Ping) msg).UUID;
		int addressUid = ((Ping) msg).addressUid;
		int type = ((Ping) msg).type;
		int serverId2 = ((Ping) msg).serverId;
		if (ClusterListener.GEUID.equals(uuid))
		{
			logger.debug("msg is ServerSupervisorMessage.Ping find self");
			return;
		}
		boolean getPing = false;
		for (MemberWaper memberWaper : members)
		{
			if (memberWaper.uid == addressUid)
			{
				getPing = true;
				memberWaper.lastPingTime = System.currentTimeMillis();
				logger.debug("msg is ServerSupervisorMessage.Pinging");
			}
		}
		if (!getPing)
		{
			MemberWaper memberWaper = new MemberWaper(serverId2, addressUid, AvalonServerMode.getSeverMode(type),
					getSender().path().address().toString());
			addMemberWaper(memberWaper);
			logger.debug("add new server by ping");
		}
		logger.debug("get ping actor " + getSender().path().address().toString());
	}

	private void processDistributionConnectionSessionsProtocol(Object msg)
	{
		logger.debug("msg is ServerSupervisorMessage.DistributionConnectionSessionsProtocol");
		int serverid = ((DistributionConnectionSessionsProtocol) msg).serverid;
		ActorRef sender = ((DistributionConnectionSessionsProtocol) msg).sender;
		Object origins = ((DistributionConnectionSessionsProtocol) msg).origins;

		CluserSessionMessage message = new CluserSessionMessage(MessageType.CluserSessionMessage, sender, origins);
		List<MemberWaper> list = getGameServer();
		/**
		 * 如果serverid<0情况，则随机分配到不同的游戏逻辑服务器
		 */
		if (serverid < 0)
		{
			logger.debug("random go to game server");
			int index = MathUtil.randomInt(0, list.size());
			MemberWaper memberWaper = list.get(index);
			String stringAddress = memberWaper.toString();
			String fixPath = AkkaPathDecorate.getFixSupervisorPath(stringAddress,
					ConnectionSessionSupervisor.IDENTIFY);

			ActorSelection actorSelection = getContext().actorSelection(fixPath);
			actorSelection.tell(message, getSelf());

		}
		else
		{
			for (MemberWaper memberWaper : list)
			{
				if (memberWaper.serverId == serverid)
				{
					logger.debug("go to game server=" + serverid);
					String stringAddress = memberWaper.address;
					String fixPath = AkkaPathDecorate.getFixSupervisorPath(stringAddress,
							ConnectionSessionSupervisor.IDENTIFY);
					ActorSelection actorSelection = getContext().actorSelection(fixPath);
					actorSelection.tell(message, getSelf());
				}
			}
		}
	}

	private void processSendRedirectMessage(Object msg)
	{
		logger.debug("msg is ServerSupervisorMessage.SendRedirectMessage");
		ReciveRedirectMessage reciveRedirectMessage = new ReciveRedirectMessage(((SendRedirectMessage) msg).sender,
				((SendRedirectMessage) msg).path, ((SendRedirectMessage) msg).message);
		DistributedPubSubMediator.Publish publish = new DistributedPubSubMediator.Publish(ClusterListener.shardName,
				reciveRedirectMessage);
		mediator.tell(publish, getSelf());
	}

	private void processReciveRedirectMessage(Object msg)
	{
		logger.debug("msg is ServerSupervisorMessage.ReciveRedirectMessage");
		String path = ((ReciveRedirectMessage) msg).path;
		ActorSelection actorSelection = getContext().actorSelection(path);
		Object message = ((ReciveRedirectMessage) msg).message;
		ActorRef sender = ((ReciveRedirectMessage) msg).sender;

		actorSelection.tell(message, sender);
	}

	private void process(Object msg)
	{
		logger.debug("msg is ServerSupervisorMessage.ServerLost");
		for (MemberWaper member : members)
		{
			if (member.uid == ((ServerLost) msg).memberUid)
			{
				members.remove(member);
				lostServer(member.serverId);
				return;
			}
		}
	}

	private void processServerOnline(Object msg, PropertiesWrapper propertiesWrapper)
	{
		ServerOnline serverOnline = (ServerOnline) msg;
		// 检查是否是自己这台机器，如果是则不加入其他机器列表
		if (serverOnline.UUID.equals(ClusterListener.GEUID))
		{
			logger.debug("msg is ServerSupervisorMessage.ServerOnline: is then same");
			return;
		}
		else
		{
			for (MemberWaper member : members)
			{
				if (serverOnline.addressUid == member.uid)
				{
					logger.debug("msg is ServerSupervisorMessage.ServerOnline:it has same" + member.uid + "=="
							+ serverOnline.addressUid + "====" + serverId);
					return;
				}
			}
			logger.debug("add new Server cool");
			MemberWaper memberWaper = new MemberWaper(serverOnline.serverId, serverOnline.addressUid,
					AvalonServerMode.getSeverMode(serverOnline.type), serverOnline.addressPath);
			addMemberWaper(memberWaper);
			chekNeedBackMessage(propertiesWrapper, serverOnline);
		}
	}
	/**
	 * 检查是不是同一个节点，如果不是则建立心跳
	 * 
	 * @param msg
	 * @param propertiesWrapper
	 */
	private void checkIsSameNodeAndCreatePing(ServerIsTheSame msg, PropertiesWrapper propertiesWrapper)
	{
		if (msg.UUID.equals(ClusterListener.GEUID))
		{
			logger.debug("msg is ServerSupervisorMessage.ServerIsTheSame find self");
			ServerSupervisorSubscriber.member = msg.member;
			this.avalonServerMode = AvalonServerMode.getSeverMode(msg.type);
			AvalonServerMode serverMode = AvalonEngine.mode;

			if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE))
			{
				serverId = propertiesWrapper.getIntProperty(SystemEnvironment.GATE_BINDING, DEFAULT_SERVRE_ID);
			}
			else
			{
				serverId = propertiesWrapper.getIntProperty(SystemEnvironment.APP_ID, DEFAULT_SERVRE_ID);
			}
			String addressStr = member.address().toString();
			int addressUid = member.uniqueAddress().uid();

			ServerOnline supervisorMessage = new ServerOnline(ClusterListener.GEUID, serverMode.type, addressStr,
					addressUid, serverId);
			mediator.tell(new DistributedPubSubMediator.Publish(ClusterListener.shardName, supervisorMessage),
					getSelf());

			ActorSystem actorSystem = AkkaDecorate.getActorSystem();
			Scheduler scheduler = actorSystem.scheduler();
			FiniteDuration delayTime = Duration.create(60, TimeUnit.SECONDS);
			FiniteDuration periodTime = Duration.create(60, TimeUnit.SECONDS);
			pingSchedule = scheduler.schedule(delayTime, periodTime, new Runnable()
			{
				@Override
				public void run()
				{
					Ping supervisorMessage = new Ping(ClusterListener.GEUID,
							addressUid, serverMode.type, serverId);
					DistributedPubSubMediator.Publish publish = new DistributedPubSubMediator.Publish(
							ClusterListener.shardName, supervisorMessage);
					mediator.tell(publish, getSelf());
				}
			}, actorSystem.dispatcher());
		}
	}

	private void chekNeedBackMessage(PropertiesWrapper propertiesWrapper, ServerOnline serverOnline)
	{
		// 检查自己是否已经获得自己机器的节点信息，并检查这个消息是否需要回复
		if (ServerSupervisorSubscriber.member != null && !serverOnline.noBack)
		{
			logger.debug("msg is ServerSupervisorMessage.ServerOnline:send back message");
			int ServerId;
			if (avalonServerMode.equals(AvalonServerMode.SERVER_TYPE_GATE))
			{
				ServerId = propertiesWrapper.getIntProperty(SystemEnvironment.GATE_BINDING, DEFAULT_SERVRE_ID);
			}
			else
			{
				ServerId = propertiesWrapper.getIntProperty(SystemEnvironment.APP_ID, DEFAULT_SERVRE_ID);
			}
			ServerOnline supervisorMessage = new ServerOnline(ClusterListener.GEUID, avalonServerMode.type,
					ServerSupervisorSubscriber.member.address().toString(),
					ServerSupervisorSubscriber.member.uniqueAddress().uid(), ServerId, true);
			getSender().tell(supervisorMessage, self());
		}
	}

	private void lostServer(int serverId)
	{
		NetServerLost message = new NetServerLost(MessageType.NetServerLost, serverId);
		AkkaDecorate.getTransportSupervisorRef().tell(message, getSelf());
	}

	private List<MemberWaper> getGameServer()
	{
		List<MemberWaper> result = new ArrayList<>();
		for (MemberWaper memberWaper : members)
		{
			if (!memberWaper.mode.equals(AvalonServerMode.SERVER_TYPE_GATE))
			{
				result.add(memberWaper);
			}
		}
		return result;
	}

}

class MemberWaper
{

	/**
	 * 服务器id
	 */
	public final int serverId;
	/**
	 * 服务器唯一id（akka生成）
	 */
	public final int uid;
	/**
	 * 服务器的启动模式
	 */
	public final AvalonServerMode mode;
	/**
	 * 服务器的IP地址
	 */
	public final String address;
	/**
	 * 最后一次ping
	 */
	public long lastPingTime;

	public MemberWaper(int serverId, int uid, AvalonServerMode mode, String address)
	{
		super();
		this.serverId = serverId;
		this.uid = uid;
		this.mode = mode;
		this.address = address;
		this.lastPingTime = System.currentTimeMillis();
	}

}