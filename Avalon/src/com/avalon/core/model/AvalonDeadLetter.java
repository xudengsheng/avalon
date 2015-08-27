package com.avalon.core.model;

import akka.actor.ActorRef;
import akka.actor.DeadLetter;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
/**
 * 自定义无效信件接收器
 * @author ZERO
 *
 */
public class AvalonDeadLetter extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof DeadLetter) {
			Object message = ((DeadLetter) arg0).message();
			ActorRef sender = ((DeadLetter) arg0).sender();
			ActorRef recipient = ((DeadLetter) arg0).recipient();
		}else{
			System.err.println(arg0);
			
		}
		
	}

}
