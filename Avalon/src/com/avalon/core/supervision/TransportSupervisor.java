package com.avalon.core.supervision;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.IoSession;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.actor.LocalTransportActor;
import com.avalon.core.actor.RemoteTransportActor;
import com.avalon.core.message.AvalonMessageEvent;
import com.avalon.core.message.TopicMessage.TransportSupervisorTopicMessage;
import com.avalon.core.message.TransportMessage;
import com.avalon.core.message.TransportSupervisorMessage;
import com.avalon.core.subscribe.TransportSupervisorTopic;
import com.avalon.setting.AvalonServerMode;

/**
 * 传输监听 分为单机模式和网关模式
 * 
 * @author ZERO
 *
 */
public class TransportSupervisor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static final String IDENTIFY = "TransportSupervisor";

	private final String localRegionPath;
	//是否是使用网关模式
	private final boolean netGateMode;

	public TransportSupervisor(String localRegionPath)
	{
		super();
		// akka://AVALON/user/sharding/ClusterConnectionSessions
		this.localRegionPath = localRegionPath;

		

		AvalonServerMode property = ContextResolver.getServerMode();
		if (property.equals(AvalonServerMode.SERVER_TYPE_GATE))
		{
			netGateMode = true;
			ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
			mediator.tell(new DistributedPubSubMediator.Subscribe(TransportSupervisorTopic.shardName, getSelf()), getSelf());
		} else
		{
			netGateMode = false;
		}
	}

	// 当前创建的会话数量
	private int transportNum = 0;

	@Override
	public void onReceive(Object msg) throws Exception
	{

		if (msg instanceof TransportSupervisorMessage.CreateIOSessionActor)
		{
			// be645988-0ff5-4e7a-bcd0-566ec1789cb7
			String sessionActorId = ((TransportSupervisorMessage.CreateIOSessionActor) msg).sessionActorId;
			IoSession ioSession = ((TransportSupervisorMessage.CreateIOSessionActor) msg).ioSession;
			// akka://AVALON/user/TransportSupervisor
			String transportSupervisorPath = getSelf().path().toString();
			Props create = null;
			if (netGateMode)
			{
				create = Props.create(RemoteTransportActor.class, sessionActorId, transportSupervisorPath, localRegionPath, ioSession);
			} else
			{
				create = Props.create(LocalTransportActor.class, sessionActorId, transportSupervisorPath, localRegionPath, ioSession);
			}

			ActorRef actorOf = getContext().actorOf(create, sessionActorId);

			getContext().watch(actorOf);
			transportNum += 1;
		}
		// 收到转发的会话
		else if (msg instanceof TransportSupervisorMessage.ReciveIOSessionMessage)
		{

			String transportPath = ((TransportSupervisorMessage.ReciveIOSessionMessage) msg).transportPath;
			ActorSelection actorSelection = getContext().actorSelection(transportPath);

			IoMessagePackage messagePackage = ((TransportSupervisorMessage.ReciveIOSessionMessage) msg).messagePackage;
			TransportMessage message = new TransportMessage.IOSessionReciveMessage(messagePackage);
			actorSelection.tell(message, getSender());
		}
		// 数据返回给JMX
		else if (msg instanceof AvalonMessageEvent.nowTransportNum)
		{
			AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
			component.handleMessage(new TransportSupervisorMessage.localTransportNum(transportNum));
		} else if (msg instanceof TransportSupervisorTopicMessage)
		{
			// TODO
		} else if (msg instanceof DistributedPubSubMediator.SubscribeAck)
		{
			System.out.println("订阅信息");
		}
		// 一个被监听Actor销毁掉了
		else if (msg instanceof Terminated)
		{
			transportNum -= 1;
		} else
		{
			unhandled(msg);
		}
	}

}
