package com.avalon.core.message;

public class Ping extends AvaloneMessage
{
	public final String UUID;

	public final int addressUid;
	
	public final int type;

	public final int serverId;

	public Ping(String uUID, int addressUid, int type, int serverId)
	{
		super(MessageType.Ping);
		UUID = uUID;
		this.addressUid = addressUid;
		this.type = type;
		this.serverId = serverId;
	}
	
	
}
