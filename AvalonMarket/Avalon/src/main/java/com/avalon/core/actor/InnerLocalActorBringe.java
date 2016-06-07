package com.avalon.core.actor;

import com.avalon.api.internal.ActorBridge;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.message.TransportMessage.CloseConnectionSessions;
import com.avalon.core.message.TransportMessage.IOSessionReciveMessage;
import com.avalon.util.AkkaDecorate;

import akka.actor.ActorRef;
import akka.actor.Inbox;

public class InnerLocalActorBringe implements ActorBridge {

	private final ActorRef self;
	public final int sessionActorUId;

	public InnerLocalActorBringe(int sessionActorUId, ActorRef self) {
		super();
		this.self = self;
		this.sessionActorUId = sessionActorUId;
	}


	@Override
	public void closed() {
		CloseConnectionSessions closed = new CloseConnectionSessions();
		Inbox inbox = AkkaDecorate.getInbox();
		inbox.send(self, closed);
	}

	@Override
	public void tellMessage(IoMessagePackage messagePackage) {
		IOSessionReciveMessage message = new IOSessionReciveMessage(messagePackage);
		Inbox inbox = AkkaDecorate.getInbox();
		inbox.send(self, message);
	}


	@Override
	public int getBindSessionId() {
		return sessionActorUId;
	}

}