package com.zero.example.actor;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.gameengine.InstanceWorld;
import com.zero.example.SessionLisenter;
import com.zero.example.message.SessionLisenterMessage;
import com.zero.example.message.UserActorMessage;
import com.zero.example.message.WorldMessage;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class UserActor extends UntypedActor {
	/**
	 * The Class selfCreator.
	 */
	static class selfCreator implements Creator<UserActor> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4506944735716145059L;

		/** The extended control. */
		private final String name;
		public final SessionLisenter sessionLisenter;

		/**
		 * Instantiates a new self creator.
		 *
		 * @param extendedControl
		 *            the extended control
		 */
		public selfCreator(String name, SessionLisenter sessionLisenter) {
			super();
			this.name = name;
			this.sessionLisenter = sessionLisenter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see akka.japi.Creator#create()
		 */
		@Override
		public UserActor create() throws Exception {
			return new UserActor(name, sessionLisenter);
		}

	}

	/**
	 * Props.
	 *
	 * @param customMessageHandler
	 *            the custom message handler
	 * @return the props
	 */
	public static Props props(String name, SessionLisenter sessionLisenter) {
		Props create = Props.create(new selfCreator(name, sessionLisenter));
		create.withDispatcher("session-default-dispatcher");
		return create;
	}

	/**
	 * Instantiates a new avalon zone manager.
	 *
	 * @param extendedControl
	 *            the extended control
	 */
	public UserActor(String name, SessionLisenter sessionLisenter) {
		super();
		this.name = name;
		this.sessionLisenter = sessionLisenter;
	}

	public final String name;
	public final SessionLisenter sessionLisenter;

	@Override
	public void postStop() throws Exception {
		super.postStop();
		WorldMessage msg = new WorldMessage.UserLeaveWorld(name);
		InstanceWorld.worldRef.tell(msg, ActorRef.noSender());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof UserActorMessage.UserTransMessage) {
			IoMessagePackage ioMessagePackage = ((UserActorMessage.UserTransMessage) message).ioMessagePackage;
			SessionLisenterMessage sessionLisenterMessage = new SessionLisenterMessage.SessionSendIoMessage(
					ioMessagePackage);
			sessionLisenter.receivedActorMessage(getSelf(), sessionLisenterMessage);

		} else if (message instanceof String) {
			System.out.println(message);
		}
	}
}
