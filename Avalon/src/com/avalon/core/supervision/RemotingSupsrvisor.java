package com.avalon.core.supervision;

import akka.actor.UntypedActor;

public class RemotingSupsrvisor extends UntypedActor{

	@Override
	public void onReceive(Object arg0) throws Exception
	{
		if (arg0 instanceof String)
		{
			System.out.println("ssssge");
		}
		
	}

}
