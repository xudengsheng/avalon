package com.avalon.core.message;

import java.io.Serializable;

import com.avalon.api.message.Packet;

/**
 * 操作avalon的事件消息
 * 
 * @author zero
 *
 */
public interface AvalonMessageEvent extends Serializable {

	public class BrocastPacket implements AvalonMessageEvent {

		public static final byte TP = 0B00000001;
		public static final byte TSP = 0B00000010;
		public static final byte CSP = 0B00000011;
		public static final byte CP = 0B00000100;

		private static final long serialVersionUID = 7804155031270659242L;

		public final byte type;

		public final Packet packet;

		public BrocastPacket(byte type, Packet packet) {
			super();
			this.type = type;
			this.packet = packet;
		}

	}

	/**
	 * 初始化Avalon
	 * 
	 * @author zero
	 *
	 */
	public static class InitAvalon implements AvalonMessageEvent {

		private static final long serialVersionUID = 5353260825760665736L;

	}

	public static class nowTransportNum implements AvalonMessageEvent {
		private static final long serialVersionUID = 5755079188868745944L;

	}

	


}
