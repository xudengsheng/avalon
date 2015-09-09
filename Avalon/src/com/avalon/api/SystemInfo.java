package com.avalon.api;

import com.avalon.setting.AvalonServerMode;

/**
 * 获取系统信息
 * @author zero
 *
 */
public interface SystemInfo {

	public AvalonServerMode getServerMode();
	
	public int getServerId();
}
