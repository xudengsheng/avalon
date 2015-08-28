package com.avalon.io.mina.fliter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.avalon.util.IobufferUtil;
import com.avalon.util.MessageHead;


public class DataCodecEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception
	{
		// 是Iobuffer的走编码，不是的直接发送
		if (message instanceof MessageHead)
		{
			IoBuffer writebuffer = IobufferUtil.getDefIoBuffer();
			writebuffer.putInt(((MessageHead) message).getPackageLegth());
			writebuffer.put(((MessageHead) message).getRawData());
			writebuffer.flip();
			out.write(writebuffer);
		}
	}

}
