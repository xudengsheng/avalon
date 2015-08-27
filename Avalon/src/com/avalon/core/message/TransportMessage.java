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
public interface TransportMessage extends Serializable {
	/**
	 * IOSession绑定TransportActor
	 * @author zero
	 *
	 */
	public static class IOSessionBindingTransportMessage implements TransportMessage {
		private static final long serialVersionUID = 3434098146106631131L;
	}

	public static class IOSessionReciveMessage implements TransportMessage {

		private static final long serialVersionUID = -7851710747490260982L;

		public final IoMessagePackage messagePackage;

		public IOSessionReciveMessage(IoMessagePackage messagePackage)
		{
			super();
			this.messagePackage = messagePackage;
		}

	}

	public static class IOSessionReciveDirectMessage implements TransportMessage {

		private static final long serialVersionUID = 3525622018824124228L;

		public final IoMessagePackage messagePackage;

		public IOSessionReciveDirectMessage(IoMessagePackage messagePackage)
		{
			super();
			this.messagePackage = messagePackage;
		}

	}

	public static class SessionSessionMessage implements TransportMessage {

		private static final long serialVersionUID = -5044288418273438309L;

		public final IoMessagePackage messagePackage;

		public SessionSessionMessage(IoMessagePackage messagePackage)
		{
			super();
			this.messagePackage = messagePackage;
		}

	}

	public static class IoSessionBinding implements TransportMessage {

		private static final long serialVersionUID = -8058485203708928666L;

		public final IoSession ioSession;

		public IoSessionBinding(IoSession ioSession)
		{
			super();
			this.ioSession = ioSession;
		}

	}

	public static class ConnectionSessionsBinding implements TransportMessage {

		private static final long serialVersionUID = -7851710747490260982L;

		public final int clusterUid;

		public ConnectionSessionsBinding(int clusterUid)
		{
			super();
			this.clusterUid = clusterUid;
		}

	}

	public static class ConnectionSessionsClosed implements TransportMessage {

		private static final long serialVersionUID = 5621353783623408296L;

	}
}
