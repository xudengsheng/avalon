package com.avalon.core.model;

import com.avalon.api.CancellableTask;

public class CancellabTaskImpl implements CancellableTask {

	public String UUID;

	public CancellabTaskImpl(String string)
	{
		this.UUID = string;
	}

	@Override
	public void cancel()
	{
		
	}

}
