package com.avalon.api.internal;

/**
 * 网络会话
 * 
 * @author ZERO
 *
 */
public interface ISession {
	/**
	 * 获得会话连接唯一ID
	 * 
	 * @return
	 */
	long getSessionId();

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
}
