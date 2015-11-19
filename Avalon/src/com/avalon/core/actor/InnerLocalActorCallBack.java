package com.avalon.core.actor;

import com.avalon.api.internal.ActorCallBack;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.AkkaServerManager;
import com.avalon.core.message.TransportMessage.CloseConnectionSessions;
import com.avalon.core.message.TransportMessage.IOSessionReciveMessage;

import akka.actor.ActorRef;

public class InnerLocalActorCallBack implements ActorCallBack {

	private final ActorRef self;
	public final int sessionActorUId;

	public InnerLocalActorCallBack(int sessionActorUId, ActorRef self) {
		super();
		this.self = self;
		this.sessionActorUId = sessionActorUId;
	}


	@Override
	public void closed() {
		CloseConnectionSessions closed = new CloseConnectionSessions();
		AkkaServerManager.getInstance().getInbox().send(self, closed);
	}

	@Override
	public void tellMessage(IoMessagePackage messagePackage) {
		IOSessionReciveMessage message = new IOSessionReciveMessage(messagePackage);
		AkkaServerManager.getInstance().getInbox().send(self, message);
	}


	@Override
	public int getBindSessionId() {
		return sessionActorUId;
	}

}