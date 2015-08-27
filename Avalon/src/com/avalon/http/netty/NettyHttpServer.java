package com.avalon.http.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.internal.IService;
import com.avalon.io.message.NetWorkMessage.SessionOnline;
import com.avalon.io.message.NetWorkMessage.SessionOutline;
import com.avalon.io.netty.NettyServer;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

/**
 * netty http 组件的
 * 
 * @author ZERO
 *
 */
public final class NettyHttpServer implements IService {

	Logger logger = LoggerFactory.getLogger(NettyServer.class);

	private final int BACKLOG = 1024;

	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private String name;
	int bossGroupNum;
	int workerGroupNum;
	int backlog;

	private AtomicInteger sessionNum = new AtomicInteger(0);

	@Override
	public void init(Object object)
	{
		logger.info("初始化Netty HTTP开始");
		PropertiesWrapper propertiesWrapper = (PropertiesWrapper) object;
		final int port=propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_HTTP_PORT,8080);
		
		bossGroupNum = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_BOSS_GROUP_NUM, Runtime.getRuntime()
				.availableProcessors() * 2);
		workerGroupNum = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_WORKER_GROUP_NUM, Runtime
				.getRuntime().availableProcessors() * 2);
		backlog = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_BACKLOG, BACKLOG);

		name = propertiesWrapper.getProperty(SystemEnvironment.NETTY_SERVER_NAME, "NETTY_SERVER");

		Thread thread = new Thread(new Runnable() {
			public void run()
			{
				bootstrap = new ServerBootstrap();
				bossGroup = new NioEventLoopGroup(bossGroupNum);
				workerGroup = new NioEventLoopGroup(workerGroupNum);
				bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, backlog).handler(new LoggingHandler(LogLevel.INFO))
						.childHandler(new NettyHttpServerInitializer());
				ChannelFuture f;
				try
				{
					f = bootstrap.bind(port).sync();
					f.channel().closeFuture().sync();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}, "NettyStartThread");
		thread.start();
		logger.info("初始化Netty 线程启动");
	}

	public void destroy(Object o)
	{
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	@Override
	public void handleMessage(Object obj)
	{
		if (obj instanceof SessionOnline)
		{
			sessionNum.getAndIncrement();
		} else if (obj instanceof SessionOutline)
		{
			sessionNum.decrementAndGet();
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	

}
