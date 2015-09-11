package com.avalon.core.service;

import com.avalon.api.DistributedTaskManager;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.actor.GameEngineActor;
import com.avalon.core.message.TaskManagerMessage;

public class DistributedTaskManagerService implements DistributedTaskManager {


	@Override
	public void scheduleTask(Runnable runnable)
	{
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
		TaskManagerMessage.createTaskMessage createTaskMessage = new TaskManagerMessage.createTaskMessage(runnable,GameEngineActor.GEUID,-1,-1);
		component.handleMessage(createTaskMessage);
	}

	@Override
	public void scheduleTask(long delay, Runnable runnable)
	{
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
		TaskManagerMessage.createTaskMessage createTaskMessage = new TaskManagerMessage.createTaskMessage(runnable,GameEngineActor.GEUID,delay,-1);
		component.handleMessage(createTaskMessage);
	}

	@Override
	public void scheduleTask(long delay, long period, Runnable runnable)
	{
		AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
		TaskManagerMessage.createTaskMessage createTaskMessage = new TaskManagerMessage.createTaskMessage(runnable,GameEngineActor.GEUID,delay,period);
		component.handleMessage(createTaskMessage);
	}

}
