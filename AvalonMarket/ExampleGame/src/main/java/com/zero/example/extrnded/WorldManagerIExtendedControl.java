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
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.gameengine.InstanceWorld;
import com.avalon.gameengine.event.IEvent;
import com.avalon.gameengine.extended.IExtendedControl;
import com.avalon.gameengine.extended.IExtendedMessage;
import com.avalon.gameengine.interfaces.ICreateANObeject;
import com.avalon.io.MessagePackImpl;
import com.example.protocol.MessageKey;
import com.example.protocol.javabean.SC_HallInfoJavaBean;
import com.example.protocol.javabean.SC_HallMessageJavaBean;
import com.example.protocol.javabean.SC_JoinHallJavaBean;
import com.example.protocol.javabean.SC_LeaveHallJavaBean;
import com.zero.example.login.LoginManager;
import com.zero.example.manager.ZoneManager;
import com.zero.example.message.UserActorMessage;
import com.zero.example.message.WorldMessage;

public class WorldManagerIExtendedControl implements IExtendedControl {

	Map<String, ActorRef> onlineUser = new TreeMap<String, ActorRef>();

	private ActorRef selfRef;
	private ICreateANObeject createANObeject;
	
	@Override
	public void handleMessage(ActorRef self, ActorRef sender, IExtendedMessage message) {
		if (message instanceof WorldMessage.UserEnterWorld) {
			Set<String> keySet = onlineUser.keySet();
			List<String> list = new ArrayList<String>();
			list.addAll(keySet);
			SC_HallInfoJavaBean hallInfo = new SC_HallInfoJavaBean();
			hallInfo.setHallId(1);
			hallInfo.setUsersName(list);
			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_HallInfo, hallInfo.getByteArray());
			UserActorMessage userMessage = new UserActorMessage.UserTransMessage(ioMessagePackage);
			sender.tell(userMessage, self);

			SC_JoinHallJavaBean bean = new SC_JoinHallJavaBean();
			bean.setName(((WorldMessage.UserEnterWorld) message).name);
			ioMessagePackage = new MessagePackImpl(MessageKey.SC_JoinHall, bean.getByteArray());
			UserActorMessage msg = new UserActorMessage.UserTransMessage(ioMessagePackage);
			for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
				entry.getValue().tell(msg, sender);
			}

			onlineUser.put(((WorldMessage.UserEnterWorld) message).name, sender);
		} else if (message instanceof WorldMessage.UserLeaveWorld) {
			onlineUser.remove(((WorldMessage.UserLeaveWorld) message).name);
			SC_LeaveHallJavaBean bean = new SC_LeaveHallJavaBean();
			bean.setName(((WorldMessage.UserLeaveWorld) message).name);

			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_LeaveHall, bean.getByteArray());
			UserActorMessage msg = new UserActorMessage.UserTransMessage(ioMessagePackage);
			for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
				entry.getValue().tell(msg, sender);
			}
		} else if (message instanceof WorldMessage.HallMessage) {
			SC_HallMessageJavaBean bean = new SC_HallMessageJavaBean();
			bean.setUsersName(((WorldMessage.HallMessage) message).decodeBean.getUsersName());
			bean.setContext(((WorldMessage.HallMessage) message).decodeBean.getContext());
			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_HallMessage, bean.getByteArray());
			UserActorMessage msg = new UserActorMessage.UserTransMessage(ioMessagePackage);
			for (Entry<String, ActorRef> entry : onlineUser.entrySet()) {
				entry.getValue().tell(msg, sender);
			}
		}
	}

	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		this.createANObeject=(ICreateANObeject)self;
		this.selfRef=(ActorRef) args[0];
		System.out.println("启动" + InstanceWorld.worldRef.path().toString() + " " + args[0]);
		
		ZoneManagerIExtendedControl managerIExtendedControl=new ZoneManagerIExtendedControl();
		ActorRef createANObject = createANObeject.createANObject(ZoneManager.class, "ZONE_MANAGER", managerIExtendedControl);
		
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
