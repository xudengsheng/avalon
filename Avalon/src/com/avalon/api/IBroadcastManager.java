package com.avalon.api;


import com.avalon.api.message.Packet;

/**
 * 广播管理器
 * @author ZERO
 *
 */
public interface IBroadcastManager  {
	/**
	 * 给传输单元发广播
	 * @param message
	 */
	public void  broadcastTransport(Packet message);
	/**
	 * 给链接会话发广播
	 * @param message
	 */
	public void  broadcastConnectionSession(Packet message);
}
