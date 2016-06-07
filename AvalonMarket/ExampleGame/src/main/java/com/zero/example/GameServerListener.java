package com.zero.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.AppContext;
import com.avalon.api.AppListener;
import com.avalon.db.MybatisDataManagerImpl;
import com.avalon.db.api.MyBatisDataManager;
import com.avalon.gameengine.InstanceWorld;
import com.avalon.util.PropertiesWrapper;
import com.zero.example.core.ExampleClientExtension;
import com.zero.example.extrnded.WorldManagerIExtendedControl;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 游戏服务器启动入口
 * 
 * @author zero
 *
 */
public class GameServerListener implements AppListener {

	public static Logger logger=LoggerFactory.getLogger("GameServerListener");
	
	private Map<String, ActorRef> sessionRef = new ConcurrentHashMap<String, ActorRef>();

	@Override
	public boolean initialize(PropertiesWrapper propertiesWrapper) {
		logger.info("GameServerListener 初始化服务");
		ActorSystem actorSystem = AppContext.getActorSystem();
		//创建顶级Actor世界
		WorldManagerIExtendedControl gameWorld = new WorldManagerIExtendedControl();
		Props props = Props.create(InstanceWorld.class, gameWorld);
		GameIndex.worldRef = actorSystem.actorOf(props, InstanceWorld.IDENTITY);
		
		MybatisDataManagerImpl dataManagerImpl=new MybatisDataManagerImpl(propertiesWrapper);
		dataManagerImpl.init(propertiesWrapper);
		AppContext.setManager(dataManagerImpl);
		
		MyBatisDataManager manager = AppContext.getManager(MyBatisDataManager.class);
		
		
		
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

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void nodeOnline(int ServerId, String nodeAddress) {
		System.out.println("Serverid"+ServerId+" node "+nodeAddress);
		ActorSelection actorSelection = AppContext.getActorSelection(nodeAddress, InstanceWorld.IDENTITY);
		actorSelection.tell("hello world", ActorRef.noSender());
	}



}
