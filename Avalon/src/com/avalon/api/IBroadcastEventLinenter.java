package com.avalon.api;

import com.avalon.api.message.Packet;

/**
 * 事件监听接口(处理自定义事件)
 * 
 * @author zero
 *
 */
public interface IBroadcastEventLinenter {

	public void handleEvent(Packet message);
	
}
