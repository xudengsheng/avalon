package com.avalon.core;

import java.io.File;

import akka.actor.ActorSystem;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
/**
 * AKKA系统初始化
 * @author ZERO
 *
 */
public class AkkaServerInitializer {
	/**
	 * 初始化
	 * @param file 配置文件
	 * @param akkaName actor系統名稱
	 * @param configName 配置文件中的名稱
	 * @return
	 */
	public static ActorSystem initActorSystem(File file,String akkaName,String configName){
		Config cg = ConfigFactory.parseFile(file);
		cg.withFallback(ConfigFactory.defaultReference(Thread.currentThread().getContextClassLoader()));
		Config config = ConfigFactory.load(cg).getConfig(configName);
		ActorSystem actorSystem=ActorSystem.create(akkaName, config);
		return actorSystem;
	}
	
}
