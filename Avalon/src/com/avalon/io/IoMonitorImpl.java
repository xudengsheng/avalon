package com.avalon.io;

import com.avalon.core.ContextResolver;
import com.avalon.io.netty.NettyServer;
import com.avalon.jmx.IoMonitorControl;

public class IoMonitorImpl implements IoMonitorControl {


	@Override
	public boolean disConnect(long sessionId) {
		NettyServer component = ContextResolver.getComponent(NettyServer.class);
		return component.disConnect(sessionId);
	}

	@Override
	public int getUnbindingSessionNum() {
		NettyServer component = ContextResolver.getComponent(NettyServer.class);
		return component.getUnbindingSessionNum();
	}

	@Override
	public int getBindingSessionNum() {
		NettyServer component = ContextResolver.getComponent(NettyServer.class);
		return component.getBindingSessionNum();
	}

	@Override
	public String getBindingSessionInfo(int index, int limit) {
		NettyServer component = ContextResolver.getComponent(NettyServer.class);
		String string = component.getBindingSessionInfo(index,limit);
		return string;
	}

	@Override
	public String getUnBindingSessionInfo(int index, int limit) {
		NettyServer component = ContextResolver.getComponent(NettyServer.class);
		String string = component.getUnBindingSessionInfo(index,limit);
		return string;
	}

}
