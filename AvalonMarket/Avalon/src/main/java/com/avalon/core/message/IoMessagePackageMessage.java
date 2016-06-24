package com.avalon.core.message;

import com.avalon.api.internal.IoMessagePackage;

public class IoMessagePackageMessage extends AvaloneMessage {
	public final IoMessagePackage messagePackage;

	public IoMessagePackageMessage(MessageType messageType, IoMessagePackage messagePackage) {
		super(messageType);
		this.messagePackage = messagePackage;
	}

}
