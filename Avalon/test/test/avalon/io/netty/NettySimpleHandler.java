package test.avalon.io.netty;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
import com.avalon.util.MessageHead;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettySimpleHandler extends ChannelHandlerAdapter implements MessageTransport {

	public NettySimpleHandler(MessageTransport transport)
	{
		super();
		this.transport = transport;
		transport.setMessageTransport(this);
	}

	private final MessageTransport transport;

	private ChannelHandlerContext ctx;

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelRegistered(ctx);
		this.ctx = ctx;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx)
	{

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		if (msg instanceof IoMessagePackage)
		{
			System.out.println("GET OP CODE ="+((IoMessagePackage) msg).getOpCode());
			System.out.println(msg);
		}
		
		transport.handleMessage(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void handleMessage(Object message)
	{
		// TODO Auto-generated method stub
		String hello = "Hello";
		IoMessagePackage ioMessagePackage = new MessagePackImpl(1, hello.getBytes());
		MessageHead head=new MessageHead(ioMessagePackage);
		ChannelFuture writeAndFlush = this.ctx.writeAndFlush(head);

	}

	@Override
	public void setMessageTransport(MessageTransport messageTransport)
	{}

}
