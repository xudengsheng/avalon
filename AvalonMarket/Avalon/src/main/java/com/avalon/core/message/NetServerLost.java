package com.avalon.core.message;

public class NetServerLost extends AvaloneMessage
{
	public final int serverId;

	public NetServerLost(MessageType messageType, int serverId)
	{
		super(messageType);
		this.serverId = serverId;
	}

}
