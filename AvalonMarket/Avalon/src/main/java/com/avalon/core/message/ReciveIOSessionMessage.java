package com.avalon.core.message;

import com.avalon.api.internal.IoMessagePackage;

public class ReciveIOSessionMessage extends AvaloneMessage
{
	public final String transportPath;

	/** The message package. */
	public final IoMessagePackage messagePackage;

	public ReciveIOSessionMessage(MessageType messageType, String transportPath,
			IoMessagePackage messagePackage)
	{
		super(messageType);
		this.transportPath = transportPath;
		this.messagePackage = messagePackage;
	}
	
	
}
