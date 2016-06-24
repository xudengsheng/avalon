package com.avalon.core.message;

import akka.actor.ActorRef;

public class ReciveRedirectMessage extends AvaloneMessage
{
	public final ActorRef sender;

	public final String path;

	public final Object message;

	public ReciveRedirectMessage(ActorRef sender, String path, Object message)
	{
		super(MessageType.ReciveRedirectMessage);
		this.sender = sender;
		this.path = path;
		this.message = message;
	}
	
	

}
