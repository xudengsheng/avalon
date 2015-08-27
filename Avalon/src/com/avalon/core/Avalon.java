package com.avalon.core;

import java.util.UUID;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.ClusterSharding;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

import com.avalon.api.IoSession;
import com.avalon.core.cluster.AvalonClusterListener;
import com.avalon.core.cluster.ConnectionSessions;
import com.avalon.core.cluster.MessageExtractor;
import com.avalon.core.message.AvalonMessageEvent;
import com.avalon.core.message.AvalonMessageEvent.BrocastPacket;
import com.avalon.core.message.AvalonTopticMessage;
import com.avalon.core.message.TopicMessage.ConnectionSessionSupervisorTopicMessage;
import com.avalon.core.message.TopicMessage.ConnectionSessionTopicMessage;
import com.avalon.core.message.TopicMessage.TransportSupervisorTopicMessage;
import com.avalon.core.message.TopicMessage.TransportTopicMessage;
import com.avalon.core.message.TransportSupervisorMessage;
import com.avalon.core.model.AvalonDeadLetter;
import com.avalon.core.subscribe.ConnectionSessionSupervisorTopic;
import com.avalon.core.subscribe.ConnectionSessionTopic;
import com.avalon.core.subscribe.TransportSupervisorTopic;
import com.avalon.core.subscribe.TransportTopic;
import com.avalon.core.supervision.ConnectionSessionSupervisor;
import com.avalon.core.supervision.GameServerSupervisor;
import com.avalon.core.supervision.MetricsListener;
import com.avalon.core.supervision.RemotingSupsrvisor;
import com.avalon.core.supervision.TransportSupervisor;
import com.avalon.setting.AvalonConstant;
import com.avalon.setting.SystemEnvironment;

/**
 * 阿瓦隆 核心
 * 
 * @author ZERO
 *
 */
public class Avalon extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	static class selfCreator implements Creator<Avalon> {

		private static final long serialVersionUID = -4506944735716145059L;

		private final ActorSystem actorSystem;

		public selfCreator(ActorSystem actorSystem)
		{
			super();
			this.actorSystem = actorSystem;
		}

		@Override
		public Avalon create() throws Exception
		{
			return new Avalon(actorSystem);
		}

	}

	public static Props props(ActorSystem actorSystem)
	{
		Props create = Props.create(new selfCreator(actorSystem));
		create.withDispatcher("session-default-dispatcher");
		return create;
	}

	public Avalon(ActorSystem actorSystem)
	{
		super();
		this.actorSystem = actorSystem;
	}

	// 集群事件监听
	private ActorRef clusterListener;
	// 性能指标
	private ActorRef metricsListener;
	// 本地的region
	private ActorRef localRegion;

	private ActorRef transportSupervisor;

	private ActorRef connectionSessionSupervisor;

	private final ActorSystem actorSystem;

	ActorRef lastSender = getContext().system().deadLetters();

	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof AvalonMessageEvent.InitAvalon)
		{

			String property = ContextResolver.getPropertiesWrapper().getProperty(SystemEnvironment.ENGINE_MODEL,AvalonConstant.SERVER_TYPE_SINGLE);

			if (property.equals(AvalonConstant.SERVER_TYPE_SINGLE))
			{
				log.info("Server model is single");
				
				connectionSessionSupervisor = actorSystem.actorOf(Props.create(ConnectionSessionSupervisor.class),ConnectionSessionSupervisor.IDENTIFY);
				this.getContext().watch(connectionSessionSupervisor);
				log.info("ConnectionSessionSupervisor path is:"+connectionSessionSupervisor.path().toString());
				transportSupervisor = actorSystem.actorOf(Props.create(TransportSupervisor.class, connectionSessionSupervisor.path().toString()),TransportSupervisor.IDENTIFY);
				this.getContext().watch(transportSupervisor);
			} else if (property.equals(AvalonConstant.SERVER_TYPE_GATE))
			{
				log.info("Server model is gate");
				clusterListener = actorSystem.actorOf(Props.create(AvalonClusterListener.class), SystemEnvironment.AVALON_CLUSTER_NAME);
				this.getContext().watch(clusterListener);

				ClusterSharding clusterSharding = ClusterSharding.get(actorSystem);
				Props RegionCreate = Props.create(ConnectionSessions.class);
				localRegion = clusterSharding.start(ConnectionSessions.shardName, RegionCreate, new MessageExtractor());
				this.context().watch(localRegion);

				transportSupervisor = actorSystem.actorOf(Props.create(TransportSupervisor.class, localRegion.path().toString()),TransportSupervisor.IDENTIFY);
				this.getContext().watch(transportSupervisor);

				Props create2 = Props.create(GameServerSupervisor.class);
				ActorRef actorOf = actorSystem.actorOf(create2, GameServerSupervisor.IDENTIFY);
				this.context().watch(actorOf);
			} else
			{
				log.info("Server model is game");
			}
			Props createDeadLetter = Props.create(AvalonDeadLetter.class);
			ActorRef actorOfDeadLetter = actorSystem.actorOf(createDeadLetter);
			actorSystem.eventStream().subscribe(actorOfDeadLetter, DeadLetter.class);

			this.metricsListener = actorSystem.actorOf(Props.create(MetricsListener.class));
			ActorRef actorOf = actorSystem.actorOf(Props.create(RemotingSupsrvisor.class), "Remoting");
			System.out.println(actorOf.path().toString());
			System.out.println(actorOf.path().address().toString());
		} else if (msg instanceof AvalonMessageEvent.IOSessionRegedit)
		{
			UUID randomUUID = UUID.randomUUID();
			IoSession ioSession = ((AvalonMessageEvent.IOSessionRegedit) msg).ioSession;

			String sessionActorId = randomUUID.toString();

			TransportSupervisorMessage.CreateIOSessionActor message = new TransportSupervisorMessage.CreateIOSessionActor(ioSession,sessionActorId);

			transportSupervisor.tell(message, getSelf());
		}
		// 转发给传输的transportSupervisor
		else if (msg instanceof TransportSupervisorMessage.ReciveIOSessionMessage)
		{
			transportSupervisor.forward(msg, getContext());
		} else if (msg instanceof AvalonMessageEvent.nowTransportNum)
		{
			transportSupervisor.forward(msg, getContext());
		}
		// 全局消息
		else if (msg instanceof AvalonTopticMessage)
		{
			ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
			mediator.tell(new DistributedPubSubMediator.Publish(TransportSupervisorTopic.shardName, msg), getSelf());
		} else if (msg instanceof String)
		{
			ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
			AvalonTopticMessage.Ping ping = new AvalonTopticMessage.Ping();
			mediator.tell(new DistributedPubSubMediator.Publish(TransportSupervisorTopic.shardName, ping), getSelf());
			// GameLogicMessage message=new
			// GameLogicMessage("Session","commandId", "remoteAddress",
			// "origins");
			// localRegion.tell(message, getSelf());
		} else if (msg instanceof AvalonMessageEvent.BrocastPacket)
		{
			byte type = ((AvalonMessageEvent.BrocastPacket) msg).type;
			switch (type)
			{
			case BrocastPacket.CP:
				tellConnectionTopic(msg);
				break;
			case BrocastPacket.TP:
				tellTransportTopic(msg);
				break;
			case BrocastPacket.CSP:
				tellConnectionSupervisorTopic(msg);
				break;
			case BrocastPacket.TSP:
				tellTransportSupervisorTopic(msg);
				break;
			default:
				break;
			}
		}
	}

	private void tellTransportSupervisorTopic(Object msg)
	{
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		TransportSupervisorTopicMessage message = new TransportSupervisorTopicMessage(((AvalonMessageEvent.BrocastPacket) msg).packet);
		mediator.tell(new DistributedPubSubMediator.Publish(TransportSupervisorTopic.shardName, message), getSelf());
	}

	private void tellConnectionSupervisorTopic(Object msg)
	{
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		ConnectionSessionSupervisorTopicMessage message = new ConnectionSessionSupervisorTopicMessage(
				((AvalonMessageEvent.BrocastPacket) msg).packet);
		mediator.tell(new DistributedPubSubMediator.Publish(ConnectionSessionSupervisorTopic.shardName, message), getSelf());
	}

	private void tellTransportTopic(Object msg)
	{
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		TransportTopicMessage message = new TransportTopicMessage(((AvalonMessageEvent.BrocastPacket) msg).packet);
		mediator.tell(new DistributedPubSubMediator.Publish(TransportTopic.shardName, message), getSelf());
	}

	private void tellConnectionTopic(Object msg)
	{
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		ConnectionSessionTopicMessage message = new ConnectionSessionTopicMessage(((AvalonMessageEvent.BrocastPacket) msg).packet);
		mediator.tell(new DistributedPubSubMediator.Publish(ConnectionSessionTopic.shardName, message), getSelf());
	}
}
