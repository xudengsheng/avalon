package com.avalon.io.mina;

import org.apache.mina.core.buffer.IoBuffer;

import com.avalon.io.MessagePackImpl;


public class MinaMessagePackImpl extends MessagePackImpl {

	private static final long serialVersionUID = -3450452341890081638L;

	public MinaMessagePackImpl(IoBuffer buffer)
	{
		super();
		super.opcode = buffer.getInt();
		super.RawData = new byte[buffer.remaining()];
		buffer.get(RawData);
	}

}
