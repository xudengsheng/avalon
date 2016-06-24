package com.avalon.exception;

public class AvalonException extends RuntimeException
{

	private static final long serialVersionUID = -5166912944428501595L;
	
	AVAErrorData errorData;

	public AvalonException()
	{
		this.errorData = null;
	}

	public AvalonException(String message)
	{
		super(message);
		this.errorData = null;
	}

	public AvalonException(String message, AVAErrorData data)
	{
		super(message);
		this.errorData = data;
	}

	public AvalonException(Throwable t)
	{
		super(t);
		this.errorData = null;
	}

	public AVAErrorData getErrorData()
	{
		return this.errorData;
	}

}
