package com.avalon.core.message;

import akka.actor.ActorRef;

public class SendRedirectMessage extends AvaloneMessage
{

	public final ActorRef sender;

	public final String path;

	public final Object message;

	public SendRedirectMessage(ActorRef sender, String path, Object message)
	{
		super(MessageType.SendRedirectMessage);
		this.sender = sender;
		this.path = path;
		this.message = message;
	}

}
