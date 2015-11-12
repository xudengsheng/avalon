package com.zero.example.extrnded;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

import com.avalon.game.extended.ExtendedMessage;
import com.avalon.game.extended.IAvalonExtendedControl;
import com.zero.example.GameIndex;
import com.zero.example.message.ZoneMessage;

public class ZoneManagerExtended implements IAvalonExtendedControl {

	private UntypedActor untypedActor;

	private int roomid;
	private int total;

	@Override
	public void handleMessage(ActorRef self, ActorRef sender, ExtendedMessage message) {
		// 创建房间
		if (message instanceof ZoneMessage.CreateRoom) {
			UntypedActorContext context = untypedActor.getContext();
		}
	}

	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		this.untypedActor = self;
		GameIndex.zoneManagerRef = self.getSelf();
		
		UntypedActorContext context = untypedActor.getContext();
//		for (int x = 1; x < 1200; x++) {
//			for (int y = 1; y < 1200; y++) {
//				ActorRef actorOf = context.actorOf(Props.create(MapBlock.class, x, y), (x + "_" + y));
//				System.out.println(x + "+" + y + " | " + (total++));
//			}
//		}
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
