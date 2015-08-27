package com.avalon.core.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.IoSession;
import com.avalon.api.internal.ActorCallBack;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.command.ConnectionSessionsProtocol;
import com.avalon.core.json.gameserversupervisor.SessionLost;
import com.avalon.core.message.ConnectionSessionMessage;
import com.avalon.core.message.TopicMessage;
import com.avalon.core.message.TopicMessage.TransportTopicMessage;
import com.avalon.core.message.TransportMessage;
import com.avalon.core.message.TransportMessage.ConnectionSessionsClosed;
import com.avalon.core.message.TransportMessage.IOSessionReciveDirectMessage;
import com.avalon.core.message.TransportMessage.IOSessionReciveMessage;
import com.avalon.core.message.TransportMessage.SessionSessionMessage;
import com.avalon.core.subscribe.GameServerSupervisorTopic;
import com.avalon.core.subscribe.TransportTopic;

/**
 * 网络会话传输actor封装 在网络会话和actor中作为桥接
 * 
 * @author ZERO
 *
 */
public class RemoteTransportActor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	// 本次会话的唯一Id，用于分布式中的分组Id
	public final String sessionActorId;

	private final ActorSelection localRegion;

	public final ActorSelection transportSupervisor;

	public ActorRef connectionSessionsRef;

	private final int clusterUid;

	private final IoSession ioSession;

	public RemoteTransportActor( String sessionid, String transportSupervisorPath,String localRegionPath, IoSession ioSession) {
		super();
		this.sessionActorId = sessionid;
		this.transportSupervisor = getContext().actorSelection(transportSupervisorPath);
		this.localRegion = getContext().actorSelection(localRegionPath);
		this.ioSession = ioSession;
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(TransportTopic.shardName, getSelf()), getSelf());

		clusterUid = Cluster.get(getContext().system()).selfUniqueAddress().uid();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof TransportMessage.IOSessionReciveMessage) {
			// 发送到集群分割器

			IoMessagePackage messagePackage = ((IOSessionReciveMessage) msg).messagePackage;

			String selfName = self().path().name();
			String parentName = self().path().parent().name();
			String sessionId = parentName + "/" + selfName;

			ConnectionSessionsProtocol commandSessionProtocol = new ConnectionSessionsProtocol(sessionActorId,sessionId, clusterUid, messagePackage);
			localRegion.tell(commandSessionProtocol, getSelf());
		}
		else if (msg instanceof IOSessionReciveDirectMessage) {
			IoMessagePackage messagePackage = ((TransportMessage.IOSessionReciveDirectMessage) msg).messagePackage;
			ConnectionSessionMessage message = new ConnectionSessionMessage.DirectSessionMessage(messagePackage);
			connectionSessionsRef.tell(message, getSelf());
		}

		else if (msg instanceof SessionSessionMessage) {
			IoMessagePackage messagePackage = ((SessionSessionMessage) msg).messagePackage;
			ioSession.write(messagePackage);
		}
		else if (msg instanceof TransportMessage.ConnectionSessionsBinding) {
			this.connectionSessionsRef = getSender();
			ioSession.setSesssionActorCallBack(new InnerActorCallBack(getSelf()));
		}
		else if (msg instanceof TransportMessage.ConnectionSessionsClosed) {
			connectionSessionsRef.tell(new ConnectionSessionMessage.LostConnect(), getSelf());
			LostSession();
		}
		else if (msg instanceof TransportTopicMessage) {
			// TODO
		}
		else {
			unhandled(msg);
		}

	}

	private void LostSession() {
		SessionLost packet = new SessionLost(clusterUid);
		TopicMessage topicMessage = new TopicMessage.GameServerSupervisorTopicMessage(packet);
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Publish(GameServerSupervisorTopic.shardName, topicMessage),getSelf());
	}

	@Override
	public void postStop() throws Exception {
		super.postStop();
		if (connectionSessionsRef != null) {
			connectionSessionsRef.tell(akka.actor.PoisonPill.getInstance(), getSelf());
			LostSession();
		}
	}

}

class InnerRemoteActorCallBack implements ActorCallBack {

	private final ActorRef self;

	public InnerRemoteActorCallBack(ActorRef self) {
		super();
		this.self = self;
	}

	@Override
	public void closed() {
		ConnectionSessionsClosed closed = new ConnectionSessionsClosed();
		self.tell(closed, ActorRef.noSender());

	}

	@Override
	public void tellMessage(IoMessagePackage messagePackage) {
		IOSessionReciveDirectMessage directMessage = new IOSessionReciveDirectMessage(messagePackage);
		self.tell(directMessage, ActorRef.noSender());
	}

}
