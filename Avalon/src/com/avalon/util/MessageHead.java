 package com.avalon.util;

import java.nio.ByteBuffer;

import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;


/**
 * 管理网络层的数据完整度，不包含具体操作码
 * 
 * @author zero
 *
 */
public class MessageHead implements IoMessage {

	private byte[] rawData;

//	public MessageHead(IoBuffer buffer)
//	{
//		super();
//		byte[] bytes = new byte[buffer.limit()];
//		buffer.get(bytes);
//		rawData = bytes;
//	}

	public MessageHead(IoMessagePackage messagePack)
	{
		super();
		ByteBuffer buffer=ByteBuffer.allocate(4+messagePack.getRawData().length);
		buffer.putInt(messagePack.getOpCode());
		buffer.put(messagePack.getRawData());
		buffer.flip();
		rawData = new byte[buffer.limit()];
		buffer.get(rawData);
	}

	/**
	 * 获取数据包长度
	 * 
	 * @return
	 */
	public int getPackageLegth()
	{
		return rawData.length;
	}

	/**
	 * 获得数据源
	 * 
	 * @return
	 */
	public byte[] getRawData()
	{
		return rawData;
	}

}
