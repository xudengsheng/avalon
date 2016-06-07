package com.avalon.util;

import com.avalon.core.AvalonMediator;
import com.avalon.core.ContextResolver;
import com.avalon.jmx.ManagementService;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;

public class AkkaDecorate {

	public static Inbox getInbox() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getInbox();
	}
	
	public static ManagementService getManagementService() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getManagementService();
	}

	public static ActorRef getAvalonActorRef() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getAvalonActorRef();
	}

	public static ActorRef getServerSupervisorSubscriberRef() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getServerSupervisorSubscriberRef();
	}

	public static ActorRef getTransportSupervisorRef() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getTransportSupervisorRef();
	}

	public static ActorRef getConnectionSessionSupervisorRef() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getConnectionSessionSupervisor();
	}

	public static ActorSystem getActorSystem() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getSystem();
	}

	public static ActorRef getGlobleTaskManagerActorRef() {
		AvalonMediator instance = ContextResolver.getComponent(AvalonMediator.class);
		return instance.getGlobleTaskManagerActor();
	}

}
