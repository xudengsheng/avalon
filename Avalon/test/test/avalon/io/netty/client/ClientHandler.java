package test.avalon.io.netty.client;

import java.lang.management.ManagementFactory;
import java.util.Date;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;

import test.avalon.io.netty.jmx.NettyClientMXBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter implements NettyClientMXBean {
	private ChannelHandlerContext ctx;
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		this.ctx=ctx;
		sendMessage(1);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void sendMessage(int command) {
		switch (command) {
		case 1:
			String hello="Hello";
			IoMessagePackage ioMessagePackage=new MessagePackImpl(1, hello.getBytes());
			ChannelFuture writeAndFlush = this.ctx.write(hello.getBytes());
			this.ctx.flush();
			break;

		default:
			break;
		}

	}
}
