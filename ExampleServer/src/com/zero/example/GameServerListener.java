package com.zero.example;

import com.avalon.api.AppListener;

/**
 * 游戏服务器启动入口
 * 
 * @author zero
 *
 */
public class GameServerListener implements AppListener {

	@Override
	public boolean initialize()
	{
		System.out.println("初始化服务");
		return true;
	}

}
