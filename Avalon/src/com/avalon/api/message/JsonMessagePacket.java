package com.avalon.api.message;

import com.alibaba.fastjson.JSON;

public abstract class JsonMessagePacket implements Packet {

	private static final long serialVersionUID = 7983382200652497855L;

	public abstract int getCodeId();

	public String toJsonMessagePacket() {
		return JSON.toJSONString(this);
	}

	@Override
	public Byte getPacketType() {
		return Packet.JSON_TYPE;
	}

}
