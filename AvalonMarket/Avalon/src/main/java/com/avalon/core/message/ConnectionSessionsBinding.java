package com.avalon.core.message;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionSessionsBinding extends AvaloneMessage {
	
	public final int serverId;

	public ConnectionSessionsBinding(MessageType messageType, int serverId) {
		super(messageType);
		this.serverId = serverId;
	}

}
