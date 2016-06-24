package com.avalon.core.message;

public class CancelTask extends AvaloneMessage
{
	public final String taskId;

	public CancelTask(String taskId)
	{
		super(MessageType.CancelTask);
		this.taskId = taskId;
	}
	
	
}
