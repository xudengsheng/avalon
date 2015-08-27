package com.avalon.core;

import com.avalon.api.IBroadcastManager;
import com.avalon.api.internal.IService;
import com.avalon.api.message.Packet;
import com.avalon.core.message.AvalonMessageEvent;

public class AvalonBrocastManager implements IService, IBroadcastManager {
	AvalonProxy component;

	@Override
	public void init(Object obj) {
		component = ContextResolver.getComponent(AvalonProxy.class);
	}

	@Override
	public void destroy(Object obj) {}

	@Override
	public void handleMessage(Object obj) {
		throw new IllegalAccessError();
	}

	@Override
	public String getName() {
		return "AvalonBrocastManager";
	}

	@Override
	public void setName(String name) {
		throw new IllegalAccessError();
	}

	@Override
	public void broadcastTransport(Packet message) {
		AvalonMessageEvent.BrocastPacket msg = new AvalonMessageEvent.BrocastPacket(
				AvalonMessageEvent.BrocastPacket.TP, message);
		component.handleMessage(msg);

	}

	@Override
	public void broadcastConnectionSession(Packet message) {
		AvalonMessageEvent.BrocastPacket msg = new AvalonMessageEvent.BrocastPacket(
				AvalonMessageEvent.BrocastPacket.CP, message);
		component.handleMessage(msg);

	}

}
