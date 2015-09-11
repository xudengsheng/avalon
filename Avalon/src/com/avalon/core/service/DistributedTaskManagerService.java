package com.avalon.core.service;

import com.avalon.api.CancellableTask;
import com.avalon.api.DistributedTaskManager;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.message.TaskManagerMessage;
import com.avalon.setting.AvalonServerMode;

public class DistributedTaskManagerService implements DistributedTaskManager {

	private final AvalonServerMode serverMode;

	public DistributedTaskManagerService(AvalonServerMode mode)
	{
		this.serverMode = mode;
	}

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
