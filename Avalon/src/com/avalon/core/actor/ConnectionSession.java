package com.avalon.core.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.ActorSession;
import com.avalon.api.ClientSessionLinenter;
import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.api.message.GetLocationMessage;
import com.avalon.core.ContextResolver;
import com.avalon.core.message.ConnectionSessionMessage;
import com.avalon.core.message.ConnectionSessionMessage.HasSenderPathMessage;
import com.avalon.core.message.GetLocationMessageImpl;
import com.avalon.core.message.TopicMessage;
import com.avalon.core.message.TransportMessage;
import com.avalon.core.message.TransportMessage.SessionSessionMessage;
import com.avalon.core.subscribe.ConnectionSessionTopic;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

/**
 * 客户端连接会话
 * 
 * @author ZERO
 *
 */
public class ConnectionSession extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private ActorSelection sender;

	private int clusterUid;

	private ActorSession clientSession;

	private ClientSessionLinenter sessionLinenter;

	@Override
	public void postStop() throws Exception
	{
		super.postStop();
	}

	@Override
	public void preStart() throws Exception
	{
		super.preStart();
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(ConnectionSessionTopic.shardName, getSelf()), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception
	{
		
		if (msg instanceof ConnectionSessionMessage.HasSenderPathMessage)
		{
			clusterUid = ((ConnectionSessionMessage.HasSenderPathMessage) msg).clusterUid;
			sender = getContext().actorSelection(((HasSenderPathMessage) msg).senderPath);

			TransportMessage.ConnectionSessionsBinding binding = new TransportMessage.ConnectionSessionsBinding(clusterUid);

			Object message = ((ConnectionSessionMessage.HasSenderPathMessage) msg).message;
			sender.tell(binding, getSelf());

			if (clientSession == null)
			{
				clientSession = new InnerClient(sender, getSelf(), getContext());
			}
			if (sessionLinenter == null)
			{
				PropertiesWrapper propertiesWrapper = ContextResolver.getPropertiesWrapper();
				sessionLinenter = (ClientSessionLinenter) propertiesWrapper.getClassInstanceProperty(
						SystemEnvironment.APP_SESSION_LISTENER, ClientSessionLinenter.class, new Class[] {});
			}
			sessionLinenter.receivedMessage(clientSession, message);
			return;
		}
		// 获得remote的Actor消息
		else if (msg instanceof ConnectionSessionMessage.DirectSessionMessage)
		{
			Object message = ((ConnectionSessionMessage.DirectSessionMessage) msg).origins;
			sessionLinenter.receivedMessage(clientSession, message);
			return;
		} else if (msg instanceof TopicMessage.ConnectionSessionTopicMessage)
		{

		}
		// 获得Actor之间的消息
		else if (msg instanceof GetLocationMessage)
		{
			sessionLinenter.receivedActorMessage(getSender(), ((GetLocationMessage) msg).getMessage());
			return;
		}
		//失去网络连接的信号
		else if(msg instanceof ConnectionSessionMessage.LostConnect){
			sessionLinenter.disconnected(false);
		}

	}

}

class InnerClient implements ActorSession {

	ActorSelection sender;

	final UntypedActorContext context;

	final ActorRef self;

	public InnerClient(ActorSelection sender, ActorRef self, UntypedActorContext untypedActorContext)
	{
		super();
		this.sender = sender;
		this.self = self;
		this.context = untypedActorContext;
	}

	@Override
	public String getSelfPath()
	{
		return self.path().toString();
	}

	@Override
	public void setTransport(ActorSelection untypActorSelection)
	{
		this.sender = untypActorSelection;

	}

	@Override
	public void sendIoMessage(IoMessagePackage message)
	{
		SessionSessionMessage sessionMessage = new SessionSessionMessage(message);
		sender.tell(sessionMessage, self);
	}

	@Override
	public void sendActorMessage(String actorPath, IoMessage message)
	{
		ActorSelection actorSelection = context.actorSelection(actorPath);
		GetLocationMessage messages = new GetLocationMessageImpl(message);
		actorSelection.tell(messages, self);

	}

}
