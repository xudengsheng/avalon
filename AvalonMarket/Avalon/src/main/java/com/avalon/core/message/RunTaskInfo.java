package com.avalon.core.message;

import com.avalon.api.CancellableTask;

public class RunTaskInfo extends AvaloneMessage
{
	private static final long serialVersionUID = -1224799178199383684L;
	
	public final CancellableTask cancellableTask;

	public RunTaskInfo( CancellableTask cancellableTask)
	{
		super(MessageType.RunTaskInfo);
		this.cancellableTask = cancellableTask;
	}
	
	
	
}
