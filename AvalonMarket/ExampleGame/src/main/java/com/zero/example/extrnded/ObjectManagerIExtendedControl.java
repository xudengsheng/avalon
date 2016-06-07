package com.zero.example.extrnded;

import com.avalon.gameengine.InstanceWorld;
import com.avalon.gameengine.extended.IExtendedControl;
import com.avalon.gameengine.extended.IExtendedMessage;
import com.zero.example.actor.UserActor;
import com.zero.example.message.ObjectManagerMesage;
import com.zero.example.message.WorldMessage;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

public class ObjectManagerIExtendedControl implements IExtendedControl {

	private UntypedActor untypedActor;

	@Override
	public void handleMessage(ActorRef self, ActorRef sender, IExtendedMessage message) {
		if (message instanceof ObjectManagerMesage.UserLogin) {
			String name = ((ObjectManagerMesage.UserLogin) message).name;
			UntypedActorContext context = untypedActor.getContext();
			ActorRef userRef = context.actorOf(Props.create(UserActor.class,name,((ObjectManagerMesage.UserLogin) message).lisenterProcess),name);
			
			WorldMessage msg=new WorldMessage.UserEnterWorld(name);
			InstanceWorld.worldRef.tell(msg, userRef);
		}
	}

	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		System.out.println("启动Obj");
		this.untypedActor = self;

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
