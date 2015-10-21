package com.zero.example.message;

import com.avalon.game.extended.ExtendedMessage;
import com.zero.example.SessionLisenter;

public interface ObjectManagerMesage extends ExtendedMessage {

	public class UserLogin implements ObjectManagerMesage {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4514813543152074330L;
		public final String name;
		public final SessionLisenter lisenterProcess;

		public UserLogin(String name, SessionLisenter lisenterProcess) {
			super();
			this.name = name;
			this.lisenterProcess = lisenterProcess;
		}

	}
}
