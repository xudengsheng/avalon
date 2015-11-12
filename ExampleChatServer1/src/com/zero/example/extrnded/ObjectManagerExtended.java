package com.zero.example.extrnded;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

import com.avalon.game.AvalonWorld;
import com.avalon.game.extended.ExtendedMessage;
import com.avalon.game.extended.IAvalonExtendedControl;
import com.zero.example.actor.UserActor;
import com.zero.example.message.ObjectManagerMesage;
import com.zero.example.message.WorldMessage;

public class ObjectManagerExtended implements IAvalonExtendedControl {

	private UntypedActor untypedActor;

	@Override
	public void handleMessage(ActorRef self, ActorRef sender, ExtendedMessage message) {
		if (message instanceof ObjectManagerMesage.UserLogin) {
			String name = ((ObjectManagerMesage.UserLogin) message).name;
			UntypedActorContext context = untypedActor.getContext();
			ActorRef userRef = context.actorOf(Props.create(UserActor.class,name,((ObjectManagerMesage.UserLogin) message).lisenterProcess),name);
			
			WorldMessage msg=new WorldMessage.UserEnterWorld(name);
			AvalonWorld.avalonWorld.tell(msg, userRef);
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
