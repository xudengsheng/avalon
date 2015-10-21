package com.zero.example.actor;

import com.avalon.game.AvalonWorld;
import com.zero.example.SessionLisenter;
import com.zero.example.actor.message.UserMessage;
import com.zero.example.actor.message.UserMessage.UserJoinHall;
import com.zero.example.hall.message.UserJoinHallBack;
import com.zero.example.hall.message.UserLeaveHallBack;
import com.zero.example.hall.message.UserMessageHallBack;
import com.zero.example.login.message.LoginBackMessage;
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
		AvalonWorld.avalonWorld.tell(msg, ActorRef.noSender());
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof UserMessage.UserLogin) {
			LoginBackMessage backMessage = new LoginBackMessage(1,
					((UserMessage.UserLogin) arg0).hallInfo.getByteArray());
			sessionLisenter.receivedActorMessage(getSelf(), backMessage);
		} else if (arg0 instanceof UserMessage.UserJoinHall) {
			UserJoinHallBack hall = new UserJoinHallBack(((UserMessage.UserJoinHall) arg0).joinHall.getByteArray());
			sessionLisenter.receivedActorMessage(getSelf(), hall);
		}
		else if (arg0 instanceof UserMessage.UserLeaveHall) {
			UserLeaveHallBack hall = new UserLeaveHallBack(((UserMessage.UserLeaveHall) arg0).joinHall.getByteArray());
			sessionLisenter.receivedActorMessage(getSelf(), hall);
		}
		else if (arg0 instanceof UserMessage.UserHallMessage) {
			UserMessageHallBack hall = new UserMessageHallBack(((UserMessage.UserHallMessage) arg0).bean.getByteArray());
			sessionLisenter.receivedActorMessage(getSelf(), hall);
		}

	}

}
