package test.avalon.io.netty.client;

import java.util.ArrayList;
import java.util.List;

import com.avalon.io.netty.filter.DataCodecDecoder;
import com.avalon.io.netty.filter.DataCodecEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HttpClient {
	public void connect(String host, int port) throws Exception {
		
		List<ChannelFuture> channelFutures=new ArrayList<ChannelFuture>();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new DataCodecDecoder());
					pipeline.addLast("encoder", new DataCodecEncoder());
					pipeline.addLast(new ClientHandler());
				}
			});
			for (int i = 0; i < 10; i++) {
				ChannelFuture f = b.connect(host, port).sync();
				channelFutures.add(f);
			}
			

		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		client.connect("127.0.0.1", 12345);
	}
}
