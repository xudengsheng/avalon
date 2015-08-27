package com.avalon.api;


import com.avalon.api.message.Packet;

/**
 * 广播管理器
 * @author ZERO
 *
 */
public interface IBroadcastManager  {

	public void  broadcastTransport(Packet message);
	
	public void  broadcastConnectionSession(Packet message);
}
