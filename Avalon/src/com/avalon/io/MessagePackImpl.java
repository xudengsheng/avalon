package com.avalon.io;

import com.avalon.api.internal.IoMessagePackage;


public class MessagePackImpl implements IoMessagePackage {

	private static final long serialVersionUID = -2782139668607241252L;
	
	protected int opcode;
	protected byte[] RawData;

	public MessagePackImpl(int opcode, byte[] rawData)
	{
		super();
		this.opcode = opcode;
		RawData = rawData;
	}

	public MessagePackImpl()
	{
		super();
	}

	@Override
	public int getOpCode()
	{
		return opcode;
	}

	@Override
	public byte[] getRawData()
	{
		return RawData;
	}

}
