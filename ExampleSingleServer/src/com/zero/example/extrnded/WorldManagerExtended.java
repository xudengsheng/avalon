package com.zero.example.extrnded;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

import com.avalon.api.AppContext;
import com.avalon.game.AvalonWorld;
import com.avalon.game.event.AkkaEvent;
import com.avalon.game.extended.ExtendedMessage;
import com.avalon.game.extended.IAvalonExtendedControl;
import com.example.protocol.HallPro.SC_HallInfo;
import com.example.protocol.javabean.SC_HallInfoJavaBean;
import com.example.protocol.javabean.SC_HallMessageJavaBean;
import com.example.protocol.javabean.SC_JoinHallJavaBean;
import com.example.protocol.javabean.SC_LeaveHallJavaBean;
import com.zero.example.actor.message.UserMessage;
import com.zero.example.login.LoginManager;
import com.zero.example.message.WorldMessage;

public class WorldManagerExtended implements IAvalonExtendedControl {

	Map<String, ActorRef> onlineUser = new TreeMap<String, ActorRef>();

	@Override
	public void handleMessage(ActorRef self, ActorRef sender, ExtendedMessage message) {
		if (message instanceof WorldMessage.UserEnterWorld) {
			Set<String> keySet = onlineUser.keySet();
			List<String> list=new ArrayList<String>();
			list.addAll(keySet);
			SC_HallInfoJavaBean hallInfo=new SC_HallInfoJavaBean();
			hallInfo.setHallId(1);
			hallInfo.setUsersName(list);
			UserMessage userMessage=new UserMessage.UserLogin(hallInfo);
			sender.tell(userMessage, self);
			
			SC_JoinHallJavaBean bean=new SC_JoinHallJavaBean();
			bean.setName(((WorldMessage.UserEnterWorld) message).name);
			UserMessage msg=new UserMessage.UserJoinHall(bean);
			for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
				entry.getValue().tell(msg, sender);
			}
			
			onlineUser.put(((WorldMessage.UserEnterWorld) message).name, sender);
		} else if (message instanceof WorldMessage.UserLeaveWorld) {
			onlineUser.remove(((WorldMessage.UserLeaveWorld) message).name);
			SC_LeaveHallJavaBean bean=new SC_LeaveHallJavaBean();
			bean.setName(((WorldMessage.UserEnterWorld) message).name);
			UserMessage msg=new UserMessage.UserLeaveHall(bean);
			for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
				entry.getValue().tell(msg, sender);
			}
		}
		 else if (message instanceof WorldMessage.HallMessage) {
				SC_HallMessageJavaBean bean=new SC_HallMessageJavaBean();
				bean.setUsersName(((WorldMessage.HallMessage) message).decodeBean.getUsersName());
				bean.setContext(((WorldMessage.HallMessage) message).decodeBean.getContext());
				UserMessage msg=new UserMessage.UserHallMessage(bean);
				for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
					entry.getValue().tell(msg, sender);
				}
			}
	}

	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		System.out.println("启动" + AvalonWorld.avalonWorld.path().toString() + " " + args);
		ZoneManagerExtended zone = new ZoneManagerExtended();
		AkkaEvent akkaEvent = new AkkaEvent.CreateZoneManager(zone);
		ActorRef self2 = self.getSelf();
		self2.tell(akkaEvent, ActorRef.noSender());

		ObjectManagerExtended objectManager = new ObjectManagerExtended();

		AkkaEvent akkaEvent2 = new AkkaEvent.CreateObjectManager(objectManager);

		self2.tell(akkaEvent2, ActorRef.noSender());

		UntypedActorContext context = self.getContext();
		ActorRef actorOf = context.actorOf(Props.create(LoginManager.class), LoginManager.class.getSimpleName());
		System.out.println(actorOf.path().toString());

		AppContext.pathCache.put(LoginManager.class.getSimpleName(), actorOf.path());

	}

	@Override
	public void actorExtendedStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actorExtendedRestart() {
		// TODO Auto-generated method stub

	}

}
