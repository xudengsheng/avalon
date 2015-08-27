package com.avalon.io.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
/**
 * 网络数据包解析
 * |4|          |4|     |……|
 * 数据包长度            操作码                数据
 * @author ZERO
 *
 */

public class DataCodecDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		if (in.readableBytes() < 4)
		{
			return;
		}

		in.markReaderIndex();

		int dataLength = in.readInt();
		if (in.readableBytes() < dataLength)
		{
			in.resetReaderIndex();
			return;
		}

		int opcode = in.readInt();

		byte[] decoded = new byte[dataLength-4];
		in.readBytes(decoded);
		IoMessagePackage messagePack = new MessagePackImpl(opcode, decoded);
		out.add(messagePack);

	}

}
