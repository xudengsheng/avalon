package com.avalon.api;

import akka.actor.ActorRef;

import com.avalon.api.internal.IoMessage;

public interface ClientSessionLinenter {
	/**
	 * 收到客户端发来的信息
	 * 
	 * @param clientSession
	 *            连接的会话
	 * @param message
	 *            消息
	 */
	void receivedMessage(ActorSession clientSession, Object message);

	/**
	 * 收到其他actor发来的消息
	 * 
	 * @param sender
	 *            发送者
	 * @param ioMessage
	 *            可序列化的消息
	 */
	void receivedActorMessage(ActorRef sender, IoMessage ioMessage);

	/**
	 * 断开网络连接后的处理
	 * 
	 * @param graceful
	 */
	void disconnected(boolean graceful);
}
