package com.avalon.api.message;

import java.io.Serializable;

public interface Packet extends Serializable {
	/**
	 * 自定义
	 */
	public static final byte Customize = 0B0000001;
	
	/**
	 * JSON
	 */
	public static final byte JSON_TYPE = 0B0000011;
	/**
	 * 获得数据包类型
	 * 
	 * @return
	 */
	public Byte getPacketType();
}
