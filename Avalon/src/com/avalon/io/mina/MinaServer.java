package com.avalon.io.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.internal.IService;
import com.avalon.io.mina.fliter.DataCodecFactory;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

public class MinaServer implements IService {

	protected Logger logger = LoggerFactory.getLogger(MinaServer.class);

	public MinaServer(int port, IoHandler handler)
	{
		super();
		this.port = port;
		this.handler = handler;
		this.codec = new ProtocolCodecFilter(new DataCodecFactory());
	}

	public MinaServer(int port, IoHandler handler, ProtocolCodecFilter protocolCodecFilter)
	{
		super();
		this.port = port;
		this.handler = handler;
		this.codec = protocolCodecFilter;

	}

	private final int port;
	private final IoHandler handler;
	private IoFilter codec;
	private IoAcceptor acceptor;
	private String name;

	@Override
	public void init(Object object)
	{
		PropertiesWrapper propertiesWrapper = (PropertiesWrapper) object;
		name = propertiesWrapper.getProperty(SystemEnvironment.MINA_SERVER_NAME, "MINA_SERVER");
		setName(SystemEnvironment.MINA_SERVER_NAME);
		acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60000);
		if (codec != null)
		{
			acceptor.getFilterChain().addLast("codec", codec);
		}
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		acceptor.setHandler(handler);
		try
		{
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e)
		{
			logger.error((getClass().getName() + ":Start Error"), e);
			e.printStackTrace();
		}
		logger.info(getClass().getName() + ":Start");
	}

	@Override
	public void destroy(Object o)
	{
		acceptor.dispose();
		logger.info(getClass().getName() + ":Stop");
	}

	@Override
	public void handleMessage(Object obj)
	{
		// TODO Auto-generated method stub

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
