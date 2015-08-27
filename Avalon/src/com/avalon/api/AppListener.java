package com.avalon.api;

/**
 * 游戏应用登入的入口
 * 
 * @author ZERO
 * 
 */
public interface AppListener {
	/**
	 * 初始化上层游戏逻辑服务器相关
	 * 
	 * @param props
	 */
	boolean initialize();

}
