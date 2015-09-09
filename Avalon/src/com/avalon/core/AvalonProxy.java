package com.avalon.core;

import java.io.File;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.avalon.api.internal.IService;
import com.avalon.core.message.AvalonMessageEvent;
import com.avalon.core.message.TaskManagerMessage;
import com.avalon.core.message.TransportSupervisorMessage;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.FileUtil;
import com.avalon.util.PropertiesWrapper;

/**
 * 阿瓦隆 的代理 基本的逻辑函数无法按照actor的形式使用，所以构建这个代理操作类
 * 
 * @author ZERO
 *
 */
public class AvalonProxy implements IService {

	private ActorSystem system;
	// avalone Actor
	private ActorRef avalonActorRef;

	private int transportnum;

	@Override
	public void init(Object obj)
	{
		File root = new File("");
		String searchPath = root.getAbsolutePath() + File.separator + "conf";
		PropertiesWrapper propertiesWrapper = (PropertiesWrapper) obj;
		String fielPath = propertiesWrapper.getProperty(SystemEnvironment.AKKA_CONFIG_PATH, searchPath);

		File config = FileUtil.scanFileByPath(fielPath, "application.conf");

		String akkaName = propertiesWrapper.getProperty(SystemEnvironment.AKKA_NAME, "AVALON");
		String configName = propertiesWrapper.getProperty(SystemEnvironment.AKKA_CONFIG_NAME, "AVALON");

		system = AkkaServerInitializer.initActorSystem(config, akkaName, configName);
		avalonActorRef = system.actorOf(Props.create(Avalon.class, system), SystemEnvironment.AVALON_NAME);

		avalonActorRef.tell(new AvalonMessageEvent.InitAvalon(), ActorRef.noSender());

	}

	@Override
	public void destroy(Object obj)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMessage(Object msg)
	{
		if (msg instanceof TransportSupervisorMessage.IOSessionRegedit)
		{
			avalonActorRef.tell(msg, ActorRef.noSender());
		} else if (msg instanceof TransportSupervisorMessage.ReciveIOSessionMessage)
		{
			avalonActorRef.tell(msg, ActorRef.noSender());
		} else if (msg instanceof AvalonMessageEvent.BrocastPacket)
		{
			avalonActorRef.tell(msg, ActorRef.noSender());
		} else if (msg instanceof String)
		{
			avalonActorRef.tell("Stt", ActorRef.noSender());
		} else if (msg instanceof TransportSupervisorMessage.localTransportNum)
		{
			this.transportnum = ((TransportSupervisorMessage.localTransportNum) msg).transprotNum;
		} else if (msg instanceof TaskManagerMessage.createTaskMessage)
		{
			avalonActorRef.tell(msg, ActorRef.noSender());
		}
	}

	@Override
	public String getName()
	{
		return "AvalonProxy";
	}

	@Override
	public void setName(String name)
	{
		throw new IllegalAccessError();
	}

	public int transportNum()
	{
		avalonActorRef.tell(new AvalonMessageEvent.nowTransportNum(), ActorRef.noSender());
		return transportnum;
	}

}
