package com.avalon.io.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.IoSession;
import com.avalon.api.internal.ActorCallBack;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.AvalonProxy;
import com.avalon.core.ContextResolver;
import com.avalon.core.message.TransportSupervisorMessage;
import com.avalon.io.message.NetWorkMessage;
import com.avalon.util.MessageHead;
import com.google.common.collect.Queues;

@Sharable
public class NettyHandler extends ChannelHandlerAdapter implements IoSession {

	private Logger logger = LoggerFactory.getLogger(getClass());
	// Channel上下文
	private ChannelHandlerContext ctx;
	// 是否绑定TransportActor
	private boolean bindingTransportActor = false;
	// 绑定TransportActor,不是连接的ConnectionSession
	private ActorCallBack transportActorCallBack;

	static AvalonProxy component = ContextResolver.getComponent(AvalonProxy.class);
	static NettyServer nettyServer = ContextResolver.getComponent(NettyServer.class);

	private Queue<Object> noProcessMessage = Queues.newLinkedBlockingDeque();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
	{
		super.handlerRemoved(ctx);
		nettyServer.handleMessage(new NetWorkMessage.SessionOutline());
		this.close();
		if (logger.isDebugEnabled())
		{
			logger.debug("解除注册");
		}

	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelRegistered(ctx);
		this.ctx = ctx;
		// 创建一个新的会话封装
		TransportSupervisorMessage.IOSessionRegedit regedit = new TransportSupervisorMessage.IOSessionRegedit(this);
		component.handleMessage(regedit);

		nettyServer.handleMessage(new NetWorkMessage.SessionOnline());
		if (logger.isDebugEnabled())
		{
			logger.debug("注册");
		}

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}

	/**
	 * 这里的msg是MessagePack
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		super.channelRead(ctx, msg);
		if (bindingTransportActor)
		{
			sendMessageToTransport(msg);
		} else
		{
			noProcessMessage.add(msg);
		}
	}

	private void sendMessageToTransport(Object msg)
	{
		IoMessagePackage ioMessagePackage = (IoMessagePackage) msg;
		transportActorCallBack.tellMessage(ioMessagePackage);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void write(Object msg)
	{
		if (msg instanceof IoMessagePackage)
		{
			MessageHead head = new MessageHead((IoMessagePackage) msg);
			ctx.writeAndFlush(head);
		}

	}

	@Override
	public boolean isConnection()
	{
		return ctx.channel().isActive();
	}

	@Override
	public void close()
	{
		ctx.close();
		if (bindingTransportActor)
		{
			transportActorCallBack.closed();
		}
	}

	@Override
	public void setSesssionActorCallBack(ActorCallBack actorCallBack)
	{
		this.transportActorCallBack = actorCallBack;
		bindingTransportActor = true;
		while (!noProcessMessage.isEmpty())
		{
			Object poll = noProcessMessage.poll();
			sendMessageToTransport(poll);
			logger.info("处理未发送的消息");
		}

	}

}
