package com.zero.example.message;

import com.avalon.api.internal.SerializableMessage;
import com.avalon.api.internal.IoMessagePackage;
import com.example.protocol.javabean.SC_HallInfoJavaBean;
import com.example.protocol.javabean.SC_HallMessageJavaBean;
import com.example.protocol.javabean.SC_JoinHallJavaBean;
import com.example.protocol.javabean.SC_LeaveHallJavaBean;

public interface UserActorMessage extends SerializableMessage {

	public class UserTransMessage implements UserActorMessage {

		private static final long serialVersionUID = -8405294738594178410L;
		
		public final IoMessagePackage ioMessagePackage;

		public UserTransMessage(IoMessagePackage ioMessagePackage) {
			super();
			this.ioMessagePackage = ioMessagePackage;
		}

	}




}
