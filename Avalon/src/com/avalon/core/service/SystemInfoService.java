package com.avalon.core.service;

import com.avalon.api.SystemInformation;
import com.avalon.setting.AvalonServerMode;

public class SystemInfoService  implements SystemInformation {

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




}
