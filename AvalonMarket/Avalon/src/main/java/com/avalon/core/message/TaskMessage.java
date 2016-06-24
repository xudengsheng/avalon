package com.avalon.core.message;

import java.util.UUID;

public class TaskMessage extends AvaloneMessage
{
	public final Runnable runnable;
	
	public final int serverID;
	
	public final long delay;
	
	public final long period;
	
	public final long createTime = System.currentTimeMillis();
	
	public final String TaskUid=UUID.randomUUID().toString();
	

	public TaskMessage(MessageType messageType, Runnable runnable, int serverID,
			long delay, long period)
	{
		super(messageType);
		this.runnable = runnable;
		this.serverID = serverID;
		this.delay = delay;
		this.period = period;
	}
	
}
