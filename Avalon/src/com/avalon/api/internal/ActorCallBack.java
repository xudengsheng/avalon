package com.avalon.api.internal;

/**
 * Actor的回调
 * 
 * @author zero
 *
 */
public interface ActorCallBack {
	/**
	 * 发送Io消息
	 * 
	 * @param messagePackage
	 */
	public void tellMessage(IoMessagePackage messagePackage);

	/**
	 * 网络关闭
	 */
	public void closed();

}
