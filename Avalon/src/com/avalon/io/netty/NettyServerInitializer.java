package com.avalon.io.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import com.avalon.io.netty.filter.DataCodecDecoder;
import com.avalon.io.netty.filter.DataCodecEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	private final Class<?> channelHandler;

	public NettyServerInitializer(Class<?> channelHandler) {
		this.channelHandler = channelHandler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
//		long transportId = NettyServer.sessionId.getAndIncrement();
		ChannelPipeline pipeline = ch.pipeline();
		ChannelHandler newInstance = (ChannelHandler) Class.forName(channelHandler.getName()).newInstance();
//				.asSubclass(channelHandler).getConstructor(long.class)
//				.newInstance(transportId);
		pipeline.addLast("decoder", new DataCodecDecoder());
		pipeline.addLast("encoder", new DataCodecEncoder());
		pipeline.addLast("handler", newInstance);
	}

}
