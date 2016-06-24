package com.avalon.core.message;

import akka.actor.ActorRef;

public class CluserSessionMessage extends AvaloneMessage {
	public final ActorRef sender;
	public final Object origins;

	public CluserSessionMessage(MessageType messageType, ActorRef sender, Object origins) {
		super(messageType);
		this.sender = sender;
		this.origins = origins;
	}

}
