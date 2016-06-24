package com.avalon.core.message;

public class ServerLost extends AvaloneMessage
{
	public final int memberUid;

	public ServerLost(int memberUid)
	{
		super(MessageType.ServerLost);
		this.memberUid = memberUid;
	}
	
	
	
}
