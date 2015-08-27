package com.avalon.core.subscribe;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 传输管理中心订阅器
 * 
 * @author zero
 *
 */
public class TransportTopic extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	public static final String shardName = "TRANSPORT_TOPIC";

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			String in = (String) msg;
			String out = in.toUpperCase();
			mediator.tell(new DistributedPubSubMediator.Publish(shardName, out), getSelf());
		}
		else {
			unhandled(msg);
		}

	}

}
