package com.avalon.core.service;

import com.avalon.api.CancellableTask;
import com.avalon.api.DistributedTaskManager;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.message.TaskManagerMessage;

public class DistributedTaskManagerService implements DistributedTaskManager {


	@Override
	public CancellableTask scheduleTask(Runnable runnable)
	{
		
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
		component.handleMessage(new TaskManagerMessage.createTaskMessage(runnable));
		return null;
	}

	@Override
	public CancellableTask scheduleTask(long delay, Runnable runnable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CancellableTask scheduleTask(long delay, long period, Runnable runnable)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
