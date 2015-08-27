package com.avalon.core;

import com.avalon.api.CancellableTask;
import com.avalon.api.GlobleTaskManager;

public class TaskManagerProxy implements GlobleTaskManager {

	@Override
	public CancellableTask scheduleGlobleOnceTask(Runnable runnable)
	{
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
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

}
