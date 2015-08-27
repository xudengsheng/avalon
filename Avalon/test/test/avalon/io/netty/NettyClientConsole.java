package test.avalon.io.netty;

import java.io.IOException;
import java.util.Scanner;

public class NettyClientConsole implements MessageTransport {

	private MessageTransport transport;
	private NettySimpleClient nettySimpleClient;

	@Override
	public void handleMessage(Object message)
	{
		if (message instanceof String)
		{

		} else if (message instanceof String[])
		{
			if (((String[]) message).length < 2)
			{
				System.out.println("params is to short");
			} else
			{
				switch (((String[]) message)[1])
				{
				case "-s":
					startClient(message);
					break;
				case "-m":
					transport.handleMessage(((String[]) message)[2]);
					break;
				default:
					break;
				}
			}
		}

	}

	private void startClient(Object message)
	{
		nettySimpleClient = new NettySimpleClient();
		try
		{
			nettySimpleClient.connect(((String[]) message)[2], Integer.parseInt(((String[]) message)[3]), this);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void setMessageTransport(MessageTransport messageTransport)
	{
		this.transport = messageTransport;
	}

	public static void main(String[] args) throws IOException
	{
		NettyClientConsole clientConsole = new NettyClientConsole();
		Inner inner = new Inner(clientConsole);
		inner.start();
	}
}

class Inner extends Thread {

	public Inner(MessageTransport mt)
	{
		super();
		this.mt = mt;
	}

	private MessageTransport mt;

	@Override
	public void run()
	{
		Scanner br = new Scanner(System.in);
		String str = null;
		while (true)
		{
			str = br.nextLine();
			String cmd;
			String[] params = null;
			if (str.contains(" "))
			{
				params = str.split(" ");
				cmd = params[0];
			} else
			{
				cmd = str;
			}
			System.out.println("your cammand is :" + str);
			switch (cmd)
			{
			case "exit":
				System.exit(1);
				break;
			case "cmd":
				mt.handleMessage(params);
				break;

			default:
				break;
			}

		}
	}
}
