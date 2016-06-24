package com.avalon.core.message;

import java.io.Serializable;

public  class AvaloneMessage implements Serializable{
	
	private static final long serialVersionUID = -3443480484969175456L;
	
	private MessageType messageType;
	
	public AvaloneMessage(MessageType messageType) {
		super();
		this.messageType = messageType;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
