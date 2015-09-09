package com.avalon.api;

import akka.actor.ActorSelection;

import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;

/**
 * 对于开发着所能使用到得和客户端相连通的接口
 * 
 * @author mac
 * 
 */
public interface ActorSession{
	/*
	 * 获取自己的地址(在当前ActorSystem中，不含有远程地址)
	 */
	String getSelfPath();

	/**
	 * 获得独立的任务调度器
	 * 
	 * @return
	 */
	TaskManager getTaskManager();

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
	 * 
	 * @param actorPath
	 *            目标Actor地址
	 * @param message
	 */
	void sendActorMessage(String actorPath, IoMessage message);

}
