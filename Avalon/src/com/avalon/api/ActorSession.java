package com.avalon.api;

import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;

import akka.actor.ActorSelection;

/**
 * 对于开发着所能使用到得和客户端相连通的接口
 * 
 * @author mac
 * 
 */
public interface ActorSession {
	/*
	 * 获取自己的地址
	 */
	String getSelfPath();

	/**
	 * 更换传输的Actor
	 * 
	 * @param untypedActor
	 */
	void setTransport(ActorSelection untypActorSelection);

	/**
	 * 发送消息(会话直接发到连接客户端)
	 * 
	 */
	void sendIoMessage(IoMessagePackage message);

	/**
	 * 向actor发送消息
	 * @param actorPath 目标Actor地址
	 * @param message
	 */
	void sendActorMessage(String actorPath,IoMessage message);
}
