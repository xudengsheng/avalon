package com.avalon.core.message;

public class ServerOnline extends AvaloneMessage
{
	public final String UUID;

	public final int type;

	public final String addressPath;

	public final int addressUid;

	public final int serverId;

	public final boolean noBack;

	public ServerOnline(String uUID, int type,
			String addressPath, int addressUid, int serverId)
	{
		super(MessageType.ServerOnline);
		UUID = uUID;
		this.type = type;
		this.addressPath = addressPath;
		this.addressUid = addressUid;
		this.serverId = serverId;
		this.noBack = false;
	}
	
	public ServerOnline(String uUID, int type,String addressPath, int addressUid, int serverId, boolean nobak) {
		super(MessageType.ServerOnline);
		this.UUID = uUID;
		this.type = type;
		this.addressPath = addressPath;
		this.addressUid=addressUid;
		this.serverId = serverId;
		this.noBack = nobak;
	}

}
