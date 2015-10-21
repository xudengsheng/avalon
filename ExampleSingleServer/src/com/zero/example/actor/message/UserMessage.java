package com.zero.example.actor.message;

import com.avalon.api.internal.IoMessage;
import com.example.protocol.javabean.SC_HallInfoJavaBean;
import com.example.protocol.javabean.SC_HallMessageJavaBean;
import com.example.protocol.javabean.SC_JoinHallJavaBean;
import com.example.protocol.javabean.SC_LeaveHallJavaBean;

public interface UserMessage extends IoMessage {

	public class UserHallMessage implements UserMessage {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4289390871768052150L;
		public final SC_HallMessageJavaBean bean;

		public UserHallMessage(SC_HallMessageJavaBean bean) {
			super();
			this.bean = bean;
		}

	}

	public class UserLogin implements UserMessage {

		private static final long serialVersionUID = -7154407627995921853L;
		public final SC_HallInfoJavaBean hallInfo;

		public UserLogin(SC_HallInfoJavaBean hallInfo) {
			super();
			this.hallInfo = hallInfo;
		}

	}

	public class UserJoinHall implements UserMessage {

		private static final long serialVersionUID = -7154407627995921853L;
		public final SC_JoinHallJavaBean joinHall;

		public UserJoinHall(SC_JoinHallJavaBean joinHall) {
			super();
			this.joinHall = joinHall;
		}

	}

	public class UserLeaveHall implements UserMessage {

		private static final long serialVersionUID = -7154407627995921853L;
		public final SC_LeaveHallJavaBean joinHall;

		public UserLeaveHall(SC_LeaveHallJavaBean joinHall) {
			super();
			this.joinHall = joinHall;
		}

	}
}
