package com.avalon.core.message;

import akka.actor.ActorRef;

public class HasSenderPathMessage extends AvaloneMessage {
	/** The cluster uid. */
	public final int serverId;
	/** The sender path. */
	public final ActorRef sender;

	/** The message. */
	public final Object message;

	public HasSenderPathMessage(MessageType messageType, int serverId, ActorRef sender, Object message) {
		super(messageType);
		this.serverId = serverId;
		this.sender = sender;
		this.message = message;
	}

}
