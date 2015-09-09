package com.avalon.core.actor;

import java.util.UUID;

import com.avalon.core.message.GameEngineMessage.NodeInfo;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class GameEngineActor extends UntypedActor {

	public static String GEUID = UUID.randomUUID().toString();

	@Override
	public void onReceive(Object arg0) throws Exception
	{
		if (arg0 instanceof NodeInfo)
		{
			if (((NodeInfo) arg0).GEUID.equals(GEUID))
			{
				System.out.println("self");
			}
			ActorRef sender = getSender();
			ActorPath path = getSender().path();
			System.out.println("ssss");
		}
	}

}
