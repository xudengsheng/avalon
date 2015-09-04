package com.zero.example;

import akka.actor.ActorRef;

import com.avalon.api.ActorSession;
import com.avalon.api.ClientSessionLinenter;
import com.avalon.api.internal.IoMessage;

/**
 * 客户端操作类
 * 
 * @author zero
 *
 */
public class SessionLisenter implements ClientSessionLinenter {

	@Override
	public void receivedMessage(ActorSession clientSession, Object message)
	{
		// 收到网络协议的
		System.out.println("收到网络协议");
	}

	@Override
	public void receivedActorMessage(ActorRef sender, IoMessage ioMessage)
	{
		// 收到其他Actor的协议
	}

	@Override
	public void disconnected(boolean graceful)
	{
		// TODO 失去网络连接的
		
	}

}
