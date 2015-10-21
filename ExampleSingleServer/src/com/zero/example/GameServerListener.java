package com.zero.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.avalon.api.AppContext;
import com.avalon.api.AppListener;
import com.avalon.game.AvalonWorld;
import com.zero.example.core.ExampleClientExtension;
import com.zero.example.extrnded.WorldManagerExtended;

/**
 * 游戏服务器启动入口
 * 
 * @author zero
 *
 */
public class GameServerListener implements AppListener {

	private Map<String, ActorRef> sessionRef = new ConcurrentHashMap<String, ActorRef>();

	@Override
	public boolean initialize() {
		System.out.println("初始化服务");
		ActorSystem actorSystem = AppContext.getActorSystem();
		WorldManagerExtended gameWorld = new WorldManagerExtended();

		Props props = Props.create(AvalonWorld.class, gameWorld);
		GameIndex.worldRef = actorSystem.actorOf(props, AvalonWorld.IDENTITY);
		
		ExampleClientExtension clientExtension = new ExampleClientExtension();
		clientExtension.init(null);
		AppContext.setManager(clientExtension);
		
		return true;
	}

	@Override
	public void actorLogin(ActorRef ref) {
		sessionRef.put(ref.path().name(), ref);
	}

	@Override
	public void actorDisconnect(String actorSessionpath) {
		sessionRef.remove(actorSessionpath);
	}

}
