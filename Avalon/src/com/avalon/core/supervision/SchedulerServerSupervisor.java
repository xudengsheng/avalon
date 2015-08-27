package com.avalon.core.supervision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.CancellableTask;
import com.avalon.api.GlobleTaskManager;
import com.avalon.api.message.JsonMessagePacket;
import com.avalon.api.message.Packet;
import com.avalon.core.json.gameserversupervisor.SessionJoin;
import com.avalon.core.json.gameserversupervisor.SessionLost;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.GameServerSupervisorMessage;
import com.avalon.core.message.GameServerSupervisorMessage.AddGameServerMember;
import com.avalon.core.message.GameServerSupervisorMessage.BlockGameServerMember;
import com.avalon.core.message.GameServerSupervisorMessage.DistributionCluserSessionMessage;
import com.avalon.core.message.SchedulerServerSupervisorMessage.RunTaskInfo;
import com.avalon.core.message.SchedulerServerSupervisorMessage;
import com.avalon.core.message.TopicMessage;
import com.avalon.core.model.CancellabTaskImpl;
import com.avalon.core.model.ServerNodeMember;
import com.avalon.core.status.GameNodeNetWorkStatus;
import com.avalon.core.subscribe.GameServerSupervisorTopic;
import com.google.common.collect.Lists;

/**
 * 游戏逻辑服务器监听 （动态分配）
 * 
 * @author ZERO
 *
 */
public class SchedulerServerSupervisor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static final String IDENTIFY = "SchedulerServerSupervisor";

	private Map<String, Cancellable> cancellableInfo = new HashMap<String, Cancellable>();

	@Override
	public void onReceive(Object arg0) throws Exception
	{
		if (arg0 instanceof SchedulerServerSupervisorMessage.RunTask)
		{
			
			CancellableTask scheduleOnceTask = null;
			switch (((SchedulerServerSupervisorMessage.RunTask) arg0).type)
			{
			case 1:
				scheduleOnceTask = scheduleOnceTask(((SchedulerServerSupervisorMessage.RunTask) arg0).runnable);
				break;
			case 2:
				scheduleOnceTask = scheduleOnceTask(((SchedulerServerSupervisorMessage.RunTask) arg0).delay,
						((SchedulerServerSupervisorMessage.RunTask) arg0).runnable);
				break;
			case 3:
				scheduleOnceTask = scheduleTask(((SchedulerServerSupervisorMessage.RunTask) arg0).delay,
						((SchedulerServerSupervisorMessage.RunTask) arg0).period,
						((SchedulerServerSupervisorMessage.RunTask) arg0).runnable);
				break;
			default:
				break;
			}
			RunTaskInfo info=new RunTaskInfo(scheduleOnceTask);
			getSender().tell(info, getSelf());
		} else if (arg0 instanceof SchedulerServerSupervisorMessage.CancelTask)
		{
			if (cancellableInfo.containsKey(((SchedulerServerSupervisorMessage.CancelTask) arg0).taskId))
			{
				Cancellable cancellable1 = cancellableInfo.get(((SchedulerServerSupervisorMessage.CancelTask) arg0).taskId);
				cancellable1.cancel();
			}
		}

	}

	public CancellableTask scheduleOnceTask(Runnable runnable)
	{
		ActorSystem actorSystem = getContext().system();
		Scheduler scheduler = actorSystem.scheduler();
		Cancellable scheduleOnce = scheduler.scheduleOnce(Duration.Zero(), runnable, actorSystem.dispatcher());
		UUID randomUUID = UUID.randomUUID();
		String string = randomUUID.toString();
		CancellabTaskImpl cancellabTaskImpl = new CancellabTaskImpl(string);
		cancellableInfo.put(string, scheduleOnce);
		return cancellabTaskImpl;
	}

	public CancellableTask scheduleOnceTask(long delay, Runnable runnable)
	{
		ActorSystem actorSystem = getContext().system();
		Scheduler scheduler = actorSystem.scheduler();
		FiniteDuration delayTime = Duration.create(delay, TimeUnit.MILLISECONDS);
		Cancellable scheduleOnce = scheduler.scheduleOnce(delayTime, runnable, actorSystem.dispatcher());
		UUID randomUUID = UUID.randomUUID();
		String string = randomUUID.toString();
		CancellabTaskImpl cancellabTaskImpl = new CancellabTaskImpl(string);
		cancellableInfo.put(string, scheduleOnce);
		return cancellabTaskImpl;
	}

	public CancellableTask scheduleTask(long delay, long period, Runnable runnable)
	{
		ActorSystem actorSystem = getContext().system();
		Scheduler scheduler = actorSystem.scheduler();
		FiniteDuration delayTime = Duration.create(delay, TimeUnit.MILLISECONDS);
		FiniteDuration periodTime = Duration.create(period, TimeUnit.MILLISECONDS);
		Cancellable schedule = scheduler.schedule(delayTime, periodTime, runnable, actorSystem.dispatcher());
		UUID randomUUID = UUID.randomUUID();
		String string = randomUUID.toString();
		CancellabTaskImpl cancellabTaskImpl = new CancellabTaskImpl(string);
		cancellableInfo.put(string, schedule);
		return cancellabTaskImpl;
	}

}
