package com.avalon.core.task;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

import com.avalon.core.message.TaskManagerMessage;
import com.google.common.collect.Maps;

/**
 * 全局任务管理器
 * 
 * @author zero
 *
 */
public class GlobleTaskManagerActor extends UntypedActor {

	public static final String shardName = "GLOBLE_TASK_MANAGER_TOPIC";

	public static final String IDENTIFY = "GlobleTaskManagerActor";
	// 周期性任务的管理
	Map<String, Map<String, Cancellable>> periodicTask = Maps.newTreeMap();

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
			long createTime = ((TaskManagerMessage.createTaskMessage) msg).createTime;
			long delay = ((TaskManagerMessage.createTaskMessage) msg).delay;
			long period = ((TaskManagerMessage.createTaskMessage) msg).period;
			Runnable runnable = ((TaskManagerMessage.createTaskMessage) msg).runnable;
			String serverUUID = ((TaskManagerMessage.createTaskMessage) msg).ServerUUID;
			ActorSystem actorSystem = getContext().system();
			//执行一次
			if (delay == -1 && period == -1)
			{
				Cancellable scheduleOnce = actorSystem.scheduler().scheduleOnce(Duration.Zero(), runnable, actorSystem.dispatcher());
			}
			//延时执行的
			else if (period == -1)
			{
				long currentTimeMillis = System.currentTimeMillis();
				long poor = currentTimeMillis - createTime;
				Cancellable scheduleOnce = actorSystem.scheduler().scheduleOnce(Duration.create(delay - poor, TimeUnit.MILLISECONDS),
						runnable, actorSystem.dispatcher());
			}
			//周期性任务
			else if (period != -1)
			{
				long currentTimeMillis = System.currentTimeMillis();
				long poor = currentTimeMillis - createTime;
				Cancellable schedule = actorSystem.scheduler().schedule(Duration.create((delay - poor), TimeUnit.MILLISECONDS),
						Duration.create(period, TimeUnit.MILLISECONDS), runnable, actorSystem.dispatcher());
				if (periodicTask.containsKey(serverUUID))
				{
					Map<String, Cancellable> map = periodicTask.get(serverUUID);
					map.put(((TaskManagerMessage.createTaskMessage) msg).TaskUid, schedule);
				} else
				{
					Map<String, Cancellable> map = Maps.newTreeMap();
					map.put(((TaskManagerMessage.createTaskMessage) msg).TaskUid, schedule);
					periodicTask.put(serverUUID, map);
				}
			}
		}
	}

}
