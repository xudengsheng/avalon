package com.avalon.exception;

public class SFSRuntimeException extends AvalonException
{

	private static final long serialVersionUID = -4368942506552917954L;
	public SFSRuntimeException()
	{
	}

	public SFSRuntimeException(String message)
	{
		super(message);
	}

	public SFSRuntimeException(Throwable t)
	{
		super(t);
	}

}
