package com.zero.example;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

import com.avalon.api.ActorSession;
import com.avalon.api.AppContext;
import com.avalon.api.ClientSessionLinenter;
import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;
import com.zero.example.core.ExampleClientExtension;
import com.zero.example.message.SessionLisenterMessage.SessionLoginMessage;
import com.zero.example.message.SessionLisenterMessage.SessionSendIoMessage;

/**
 * 客户端操作类
 * 
 * @author zero
 *
 */
public class SessionLisenter implements ClientSessionLinenter {

	UntypedActor selfUntypedActor;
	UntypedActorContext context;
	ActorRef self;
	ActorSession clientSession;

	ActorRef user;

	@Override
	public void receivedMessage(ActorSession clientSession, IoMessagePackage message) {
		// 收到网络协议的
		this.clientSession = clientSession;
		selfUntypedActor = clientSession.getSelfUntypedActor();
		context = selfUntypedActor.getContext();
		self = selfUntypedActor.getSelf();

		int opCode = ((IoMessagePackage) message).getOpCode();
		byte[] rawData = ((IoMessagePackage) message).getRawData();

		ExampleClientExtension clientExtension = AppContext.getManager(ExampleClientExtension.class);
		clientExtension.handleClientRequest(this, opCode, rawData);
	}

	@Override
	public void receivedActorMessage(ActorRef sender, IoMessage ioMessage) {
		// 收到其他Actor的协议
		if (ioMessage instanceof SessionLoginMessage) {
			clientSession.sendIoMessage(((SessionLoginMessage) ioMessage).ioMessage);
			this.user = sender;
		} else if (ioMessage instanceof SessionSendIoMessage) {
			clientSession.sendIoMessage(((SessionSendIoMessage) ioMessage).ioMessagePackage);
		}
	}

	@Override
	public void disconnected(boolean graceful) {
		// TODO 失去网络连接的
		if (user != null) {
			user.tell(PoisonPill.getInstance(), ActorRef.noSender());
		}
	}

	public UntypedActor getSelfUntypedActor() {
		return selfUntypedActor;
	}

	public void setSelfUntypedActor(UntypedActor selfUntypedActor) {
		this.selfUntypedActor = selfUntypedActor;
	}

	public UntypedActorContext getContext() {
		return context;
	}

	public void setContext(UntypedActorContext context) {
		this.context = context;
	}

	public ActorRef getSelf() {
		return self;
	}

	public void setSelf(ActorRef self) {
		this.self = self;
	}

}
