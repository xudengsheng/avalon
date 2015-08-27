package com.avalon.api;

import com.avalon.api.internal.ActorCallBack;

/**
 * 网络会话
 * 
 * @author ZERO
 *
 */
public interface IoSession {

	/**
	 * 数据写入
	 * 
	 * @param msg
	 */
	void write(Object msg);

	/**
	 * 连接是否正常
	 * 
	 * @return
	 */
	boolean isConnection();

	/**
	 * 关闭连接
	 */
	void close();

	/**
	 * 设置完成初始化回调函数
	 * 
	 * @param actorCallBack
	 */
	void setSesssionActorCallBack(ActorCallBack actorCallBack);

}
