package com.avalon.core.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.IoSession;
import com.avalon.api.internal.ActorCallBack;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.message.ConnectionSessionMessage;
import com.avalon.core.message.GameServerSupervisorMessage.LocalSessionMessage;
import com.avalon.core.message.TopicMessage.TransportTopicMessage;
import com.avalon.core.message.TransportMessage;
import com.avalon.core.message.TransportMessage.ConnectionSessionsClosed;
import com.avalon.core.message.TransportMessage.IOSessionReciveMessage;
import com.avalon.core.message.TransportMessage.SessionSessionMessage;
import com.avalon.core.subscribe.TransportTopic;

/**
 * 网络会话传输actor封装 在网络会话和actor中作为桥接
 * 
 * @author ZERO
 *
 */
public class LocalTransportActor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	// 本次会话的唯一Id，用于分布式中的分组Id
	public final String sessionActorId;

	private final ActorSelection ConnectionSessionSupervisor;

	public final ActorSelection transportSupervisor;

	public ActorRef connectionSessionsRef;

	private final IoSession ioSession;
	// 是否绑定Session连接会话
	private boolean bindingConnectionSession;

	public LocalTransportActor(String sessionid, String transportSupervisorPath, String gameServerSupervisor, IoSession ioSession)
	{
		super();
		this.sessionActorId = sessionid;
		this.transportSupervisor = getContext().actorSelection(transportSupervisorPath);
		this.ConnectionSessionSupervisor = getContext().actorSelection(gameServerSupervisor);
		this.ioSession = ioSession;
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(TransportTopic.shardName, getSelf()), getSelf());
		getSelf().tell(new TransportMessage.IOSessionBindingTransportMessage(), ActorRef.noSender());
	}

	@Override
	public void onReceive(Object msg) throws Exception
	{

		if (msg instanceof TransportMessage.IOSessionBindingTransportMessage)
		{
			ioSession.setSesssionActorCallBack(new InnerActorCallBack(getSelf()));
		} else if (msg instanceof TransportMessage.IOSessionReciveMessage)
		{
			if (bindingConnectionSession)
			{
				IoMessagePackage messagePackage = ((IOSessionReciveMessage) msg).messagePackage;
				ConnectionSessionMessage message = new ConnectionSessionMessage.DirectSessionMessage(messagePackage);
				connectionSessionsRef.tell(message, getSelf());
			}else{
				IoMessagePackage messagePackage = ((IOSessionReciveMessage) msg).messagePackage;
				LocalSessionMessage commandSessionProtocol = new LocalSessionMessage(messagePackage);
				ConnectionSessionSupervisor.tell(commandSessionProtocol, getSelf());
			}
			
		} 
//		else if (msg instanceof IOSessionReciveDirectMessage)
//		{
//			IoMessagePackage messagePackage = ((TransportMessage.IOSessionReciveDirectMessage) msg).messagePackage;
//			ConnectionSessionMessage message = new ConnectionSessionMessage.DirectSessionMessage(messagePackage);
//			connectionSessionsRef.tell(message, getSelf());
//		}
		else if (msg instanceof SessionSessionMessage)
		{
			IoMessagePackage messagePackage = ((SessionSessionMessage) msg).messagePackage;
			ioSession.write(messagePackage);
		} else if (msg instanceof TransportMessage.ConnectionSessionsClosed)
		{
			connectionSessionsRef.tell(new ConnectionSessionMessage.LostConnect(), getSelf());
		} else if (msg instanceof TransportTopicMessage)
		{
			// TODO

		} else if (msg instanceof TransportMessage.ConnectionSessionsBinding)
		{
			this.connectionSessionsRef = getSender();
			this.bindingConnectionSession = true;
		} else
		{
			unhandled(msg);
		}

	}

	@Override
	public void postStop() throws Exception
	{
		super.postStop();
	}

}

class InnerActorCallBack implements ActorCallBack {

	private final ActorRef self;

//	private boolean sendFristMessage;

	public InnerActorCallBack(ActorRef self)
	{
		super();
		this.self = self;
	}

	@Override
	public void closed()
	{
		ConnectionSessionsClosed closed = new ConnectionSessionsClosed();
		self.tell(closed, ActorRef.noSender());

	}

	@Override
	public void tellMessage(IoMessagePackage messagePackage)
	{
		// if (sendFristMessage)
		// {
		// IOSessionReciveDirectMessage directMessage = new
		// IOSessionReciveDirectMessage(messagePackage);
		// self.tell(directMessage, ActorRef.noSender());
		// }else{
		IOSessionReciveMessage message = new IOSessionReciveMessage(messagePackage);
		self.tell(message, ActorRef.noSender());
//		sendFristMessage = true;
		// }

	}

}
