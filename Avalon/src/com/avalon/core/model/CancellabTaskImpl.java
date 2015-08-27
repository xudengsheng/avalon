package com.avalon.core.model;

import com.avalon.api.CancellableTask;
import com.sun.xml.internal.ws.api.server.ContainerResolver;

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
