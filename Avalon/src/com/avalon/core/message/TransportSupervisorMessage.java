package com.avalon.core.message;

import java.io.Serializable;

import com.avalon.api.IoSession;
import com.avalon.api.internal.IoMessagePackage;

/**
 * 网络通讯消息
 * 
 * @author ZERO
 *
 */
public interface TransportSupervisorMessage extends Serializable {

	/**
	 * 网络创建会话
	 * 
	 * @author ZERO
	 *
	 */
	public static class CreateIOSessionActor implements TransportSupervisorMessage {
		private static final long serialVersionUID = -631426557637927828L;
		// 网络会话
		public final IoSession ioSession;
		// 会话ID，IoSession和Actor绑定
		public final String sessionActorId;

		public CreateIOSessionActor(IoSession ioSession, String sessionActorId) {
			super();
			this.ioSession = ioSession;
			this.sessionActorId = sessionActorId;
		}
	}

	/**
	 * 网络关闭会话
	 * 
	 * @author ZERO
	 *
	 */
	public static class Closing implements TransportSupervisorMessage {
		private static final long serialVersionUID = -631426557337927828L;

		public final String sessionId;

		public Closing(String sessionId) {
			super();
			this.sessionId = sessionId;
		}

	}

	public static class Connecting implements TransportSupervisorMessage {
		private static final long serialVersionUID = -631426517637927828L;

	}


	/**
	 * 收到网络消息
	 * 
	 * @author ZERO
	 *
	 */
	public static class ReciveIOSessionMessage implements TransportSupervisorMessage {

		private static final long serialVersionUID = -631426557637927828L;
		// 网络会话
		public final String transportPath;

		public final IoMessagePackage messagePackage;

		public ReciveIOSessionMessage(String transportPath, IoMessagePackage messagePackage) {
			super();
			this.transportPath = transportPath;
			this.messagePackage = messagePackage;
		}

	}
	
	
	/**
	 * 本地Transport数量查询
	 * 
	 * @author zero
	 *
	 */
	public class localTransportNum implements TransportSupervisorMessage {
		/**
		 * 
		 */
		private static final long serialVersionUID = -914733653432627246L;
		public final int transprotNum;

		public localTransportNum(int transprotNum) {
			super();
			this.transprotNum = transprotNum;
		}

	}


	/**
	 * 网络创建会话 发送到代理
	 * 
	 * @author ZERO
	 *
	 */
	public static class IOSessionRegedit implements TransportSupervisorMessage {
		private static final long serialVersionUID = -631426557637927828L;
		// 网络会话
		public final IoSession ioSession;

		public IOSessionRegedit(IoSession ioSession) {
			super();
			this.ioSession = ioSession;
		}

	}

}
