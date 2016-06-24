package com.avalon.core.message;

public class TransportBindingServer extends AvaloneMessage
{
	public final int serverId;

	public TransportBindingServer(MessageType messageType, int serverId)
	{
		super(messageType);
		this.serverId = serverId;
	}

}
