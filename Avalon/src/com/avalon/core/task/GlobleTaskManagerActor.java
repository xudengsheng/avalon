package com.avalon.core.task;

import com.avalon.core.message.TaskManagerMessage;
import com.avalon.setting.AvalonServerMode;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

/**
 * 全局任务管理器
 * 
 * @author zero
 *
 */
public class GlobleTaskManagerActor extends UntypedActor {

	public static final String shardName = "GLOBLE_TASK_MANAGER_TOPIC";

	public static final String IDENTIFY = "GlobleTaskManagerActor";

	@Override
	public void preStart() throws Exception
	{
		super.preStart();
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(GlobleTaskManagerActor.shardName, getSelf()), getSelf());

	}

	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof TaskManagerMessage.createTaskMessage)
		{
			
		}
	}

}
