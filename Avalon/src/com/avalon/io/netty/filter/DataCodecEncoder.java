package com.avalon.io.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.avalon.util.MessageHead;


public class DataCodecEncoder extends MessageToByteEncoder<MessageHead> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageHead msg, ByteBuf out) throws Exception
	{
		out.writeInt(msg.getPackageLegth());
		out.writeBytes(msg.getRawData());
	}

}
