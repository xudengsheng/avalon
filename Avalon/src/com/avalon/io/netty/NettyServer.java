package com.avalon.io.netty;

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
import com.avalon.jmx.IoMonitorMXBean;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

/**
 * NETTY网络服务
 * 
 * @author ZERO
 *
 */
public class NettyServer implements IoMonitorMXBean, IService {

	Logger logger = LoggerFactory.getLogger(NettyServer.class);

	private final int BACKLOG = 1024;

	public NettyServer(int port, Class<?> clazz) {
		super();
		this.port = port;
		this.channelHandler = clazz;
	}

	private final int port;
	private final Class<?> channelHandler;

	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private String name;
	int bossGroupNum;
	int workerGroupNum;
	int backlog;

	private AtomicInteger sessionNum = new AtomicInteger(0);

	@Override
	public void init(Object object) {
		logger.info("初始化Netty 开始");
		PropertiesWrapper propertiesWrapper = (PropertiesWrapper) object;

		int defaultValue = Runtime.getRuntime().availableProcessors() * 2;
		
		bossGroupNum = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_BOSS_GROUP_NUM, defaultValue);
		workerGroupNum = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_WORKER_GROUP_NUM, defaultValue);
		backlog = propertiesWrapper.getIntProperty(SystemEnvironment.NETTY_BACKLOG, BACKLOG);

		name = propertiesWrapper.getProperty(SystemEnvironment.NETTY_SERVER_NAME, "NETTY_SERVER");

		Thread thread = new Thread(new Runnable() {
			public void run() {
				bootstrap = new ServerBootstrap();
				bossGroup = new NioEventLoopGroup(bossGroupNum);
				workerGroup = new NioEventLoopGroup(workerGroupNum);
				bootstrap.group(bossGroup, workerGroup)
						.channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, backlog)
						.handler(new LoggingHandler(LogLevel.INFO))
						.childHandler(new NettyServerInitializer(channelHandler));
				ChannelFuture f;
				try {
					f = bootstrap.bind(port).sync();
					f.channel().closeFuture().sync();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "NettyStartThread");
		thread.start();
		logger.info("初始化Netty 线程启动");
	}

	public void destroy(Object o) {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	@Override
	public void handleMessage(Object obj) {
		if (obj instanceof SessionOnline) {
			sessionNum.getAndIncrement();
		}
		else if (obj instanceof SessionOutline) {
			sessionNum.decrementAndGet();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getSessionNum() {
		return sessionNum.get();
	}

	@Override
	public boolean disConnect(long sessionId) {
		return false;
	}

}
