package com.avalon.core.message;

public class RunTask extends AvaloneMessage
{

	
	public final long delay;
	
	public final long period;
	
	public final Runnable runnable;
	
	public final int type;

	public RunTask(Runnable runnable)
	{
		super(MessageType.RunTask);
		this.type = 1;
		this.delay = 0;
		this.period = 0;
		this.runnable = runnable;
	}

	public RunTask(long delay, Runnable runnable)
	{
		super(MessageType.RunTask);
		this.type = 2;
		this.delay = delay;
		this.period = 0;
		this.runnable = runnable;
	}

	public RunTask(long delay, long period, Runnable runnable)
	{
		super(MessageType.RunTask);
		this.type = 3;
		this.delay = delay;
		this.period = period;
		this.runnable = runnable;
	}


}
