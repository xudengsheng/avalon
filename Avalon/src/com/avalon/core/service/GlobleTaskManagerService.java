package com.avalon.core.service;

import com.avalon.api.CancellableTask;
import com.avalon.api.GlobleTaskManager;
import com.avalon.api.internal.IService;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.message.TaskManagerMessage;

public class GlobleTaskManagerService implements IService,GlobleTaskManager {

	@Override
	public CancellableTask scheduleGlobleOnceTask(Runnable runnable)
	{
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
		component.handleMessage(new TaskManagerMessage.createTaskMessage(runnable));
		return null;
	}

	@Override
	public CancellableTask scheduleGlobleOnceTask(long delay, Runnable runnable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CancellableTask scheduleGlobleTask(long delay, long period, Runnable runnable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		return GlobleTaskManagerService.class.getSimpleName();
	}

	@Override
	public void setName(String name)
	{
		
	}

}
