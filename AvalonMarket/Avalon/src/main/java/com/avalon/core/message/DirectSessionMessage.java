package com.avalon.core.message;

public class DirectSessionMessage extends AvaloneMessage {
	
	public DirectSessionMessage(MessageType messageType, Object origins) {
		super(messageType);
		this.origins = origins;
	}

	public Object origins;

}
