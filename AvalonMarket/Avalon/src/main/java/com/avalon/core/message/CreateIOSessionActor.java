package com.avalon.core.message;

import com.avalon.api.IoSession;

public class CreateIOSessionActor extends AvaloneMessage
{
	public final IoSession ioSession;

	public CreateIOSessionActor(MessageType messageType, IoSession ioSession)
	{
		super(messageType);
		this.ioSession = ioSession;
	}
	
	
}
