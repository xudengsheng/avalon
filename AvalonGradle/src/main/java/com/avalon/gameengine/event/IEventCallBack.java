package com.avalon.gameengine.event;

import akka.actor.ActorRef;

public interface IEventCallBack {

	public void callBack(ActorRef actorRef);
	
}
