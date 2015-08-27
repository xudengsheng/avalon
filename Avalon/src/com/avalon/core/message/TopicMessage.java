package com.avalon.core.message;

import java.io.Serializable;

import com.avalon.api.message.Packet;

/**
 * 订阅消息
 * 
 * @author ZERO
 *
 */
public interface TopicMessage extends Serializable {

	public class ConnectionSessionSupervisorTopicMessage implements TopicMessage {

		private static final long serialVersionUID = 1777458262966587266L;

		public final Packet packet;

		public ConnectionSessionSupervisorTopicMessage(Packet packet) {
			super();
			this.packet = packet;
		}

	}

	public class ConnectionSessionTopicMessage implements TopicMessage {

		private static final long serialVersionUID = -7312216090345242754L;

		public final Packet packet;

		public ConnectionSessionTopicMessage(Packet packet) {
			super();
			this.packet = packet;
		}
	}

	public class TransportSupervisorTopicMessage implements TopicMessage {

		private static final long serialVersionUID = -6362159272599471094L;
		public final Packet packet;

		public TransportSupervisorTopicMessage(Packet packet) {
			super();
			this.packet = packet;
		}

	}

	public class TransportTopicMessage implements TopicMessage {

		private static final long serialVersionUID = -2680777997214859898L;
		public final Packet packet;

		public TransportTopicMessage(Packet packet) {
			super();
			this.packet = packet;
		}

	}
	public class GameServerSupervisorTopicMessage implements TopicMessage {
		
		private static final long serialVersionUID = -8412306264483793449L;
		
		public final Packet packet;
		
		public GameServerSupervisorTopicMessage(Packet packet) {
			super();
			this.packet = packet;
		}
		
	}
}
