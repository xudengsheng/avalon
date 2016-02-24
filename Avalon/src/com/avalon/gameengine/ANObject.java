package com.avalon.gameengine;

import com.avalon.gameengine.extended.IExtendedControl;
import com.avalon.gameengine.extended.IExtendedMessage;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public abstract class ANObject extends UntypedActor implements IExtendedControl {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	public ANObject(IExtendedControl extended) {
		super();
		this.extended = extended;
	}

	private final IExtendedControl extended;

	@Override
	public  void onReceive(Object message) throws Exception {
		if (message instanceof IExtendedMessage) {
			this.handleMessage(getSelf(), getSender(), (IExtendedMessage) message);
		}
	}

	@Override
	public void handleMessage(ActorRef self, ActorRef sender, IExtendedMessage message) {
		extended.handleMessage(getSelf(), getSender(), (IExtendedMessage) message);
	}

	
	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actorExtendedStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actorExtendedRestart() {
		// TODO Auto-generated method stub
		
	}
}
