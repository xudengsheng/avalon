package com.avalon.core.service;

import com.avalon.api.SystemInfo;
import com.avalon.api.internal.IService;
import com.avalon.setting.AvalonServerMode;

public class SystemInfoService implements SystemInfo, IService {

	private AvalonServerMode mode;

	private int servierId;

	@Override
	public AvalonServerMode getServerMode()
	{
		return mode;
	}

	@Override
	public int getServerId()
	{
		return servierId;
	}

	public AvalonServerMode getMode()
	{
		return mode;
	}

	public void setMode(AvalonServerMode mode)
	{
		this.mode = mode;
	}

	public int getServierId()
	{
		return servierId;
	}

	public void setServierId(int servierId)
	{
		this.servierId = servierId;
	}

	@Override
	public void init(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name)
	{
		// TODO Auto-generated method stub
		
	}

}
