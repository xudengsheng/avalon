package com.zero.example.message;

import com.avalon.api.internal.SerializableMessage;
import com.avalon.api.internal.IoMessagePackage;

public interface SessionLisenterMessage extends SerializableMessage {

	public class SessionLoginMessage implements SessionLisenterMessage {

		private static final long serialVersionUID = -7672767038563741024L;

		public final IoMessagePackage ioMessage;

		public SessionLoginMessage(IoMessagePackage ioMessage) {
			super();
			this.ioMessage = ioMessage;
		}

	}

	/**
	 * 发送消息给客户端
	 * 
	 * @author zero
	 *
	 */
	public class SessionSendIoMessage implements SessionLisenterMessage {

		private static final long serialVersionUID = 7081334043664342590L;

		public final IoMessagePackage ioMessagePackage;

		public SessionSendIoMessage(IoMessagePackage ioMessagePackage) {
			super();
			this.ioMessagePackage = ioMessagePackage;
		}

	}

}
