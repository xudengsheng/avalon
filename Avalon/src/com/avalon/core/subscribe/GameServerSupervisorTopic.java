package com.avalon.core.subscribe;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.message.Packet;
import com.avalon.core.message.TopicMessage.GameServerSupervisorTopicMessage;

/**
 * 传输管理中心订阅器（广播到所有逻辑服务器的管理节点）
 * 
 * @author zero
 *
 */
public class GameServerSupervisorTopic extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	public static final String shardName = "GAME_SERVER_SUPERVISOR_TOPIC_TOPIC";

	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof GameServerSupervisorTopicMessage)
		{
			Packet packet = ((GameServerSupervisorTopicMessage) msg).packet;
			mediator.tell(new DistributedPubSubMediator.Publish(shardName, packet), getSelf());
		} else
		{
			unhandled(msg);
		}

	}

}
