package com.zero.example.message;

import com.avalon.game.extended.ExtendedMessage;
import com.example.protocol.javabean.CS_HallMessageJavaBean;

public interface WorldMessage extends ExtendedMessage {

	public class HallMessage implements WorldMessage {
		
		private static final long serialVersionUID = -3661517703705112039L;
		public final CS_HallMessageJavaBean decodeBean;
		
		public HallMessage(CS_HallMessageJavaBean decodeBean) {
			this.decodeBean=decodeBean;
		}

	}

	public class UserEnterWorld implements WorldMessage {

		private static final long serialVersionUID = -354245771611565523L;

		public final String name;

		public UserEnterWorld(String name) {
			super();
			this.name = name;
		}

	}
	
	public class UserLeaveWorld implements WorldMessage {

		private static final long serialVersionUID = -354245771611565523L;

		public final String name;

		public UserLeaveWorld(String name) {
			super();
			this.name = name;
		}

	}
}
