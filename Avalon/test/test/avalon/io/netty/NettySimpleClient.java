package test.avalon.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.avalon.io.netty.filter.DataCodecDecoder;
import com.avalon.io.netty.filter.DataCodecEncoder;

public class NettySimpleClient {
	ChannelFuture f;

	public void connect(String host, int port, MessageTransport messageTransport) throws Exception
	{
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception
				{
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new DataCodecDecoder());
					pipeline.addLast("encoder", new DataCodecEncoder());
					pipeline.addLast(new NettySimpleHandler(messageTransport));
				}
			});
			ChannelFuture f = b.connect(host, port).sync();
			if (f.isSuccess())
			{
				System.out.println("连接建立");
			}
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}

	}
}
