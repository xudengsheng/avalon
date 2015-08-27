package com.avalon.core.cluster;

import akka.contrib.pattern.ShardRegion;

import com.avalon.core.command.ConnectionSessionsProtocol;

/**
 * Actor分配策略
 * 
 * @author ZERO
 *
 */
public class MessageExtractor implements ShardRegion.MessageExtractor {

	
	@Override
	public String entryId(Object msg) {
		if (msg instanceof ConnectionSessionsProtocol) {
			return ((ConnectionSessionsProtocol) msg).sessionId;
		}
		else {
			return "";
		}
	}

	@Override
	public Object entryMessage(Object msg) {
//		if (msg instanceof AvalonProtocol) {
//			TransportProtocol protocol = new TransportProtocol(((AvalonProtocol) msg).remoteAddress,
//					((AvalonProtocol) msg).getOrigins());
//			return protocol;
//		}
//		else {
			return msg;
//		}
	}

	@Override
	public String shardId(Object msg) {
		if (msg instanceof ConnectionSessionsProtocol) {
			return String.valueOf((Math.abs(((ConnectionSessionsProtocol) msg).sessionId.hashCode()) % 100));
		}
		else {
			return "";
		}

	}
}
