package com.avalon.gameengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.gameengine.extended.IExtendedControl;
import com.avalon.gameengine.extended.IExtendedMessage;
import com.avalon.util.LogUtil;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
/**
 * 抽象Avalon的Actor封装对象
 * @author zero
 *
 */
public abstract class ANObject extends UntypedActor implements IExtendedControl {

	Logger logger=LoggerFactory.getLogger("ANObject");

	public ANObject(IExtendedControl extended) {
		super();
		this.extended = extended;
		LogUtil.debugLog("AvaLongObecj init="+getClass().getName());
	}
	/**
	 * 消息处理Handler
	 */
	private final IExtendedControl extended;

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof IExtendedMessage) {
			this.handleMessage(getSelf(), getSender(), (IExtendedMessage) message);
		}
	}

	
	
	@Override
	public void handleMessage(ActorRef self, ActorRef sender, IExtendedMessage message) {
		extended.handleMessage(getSelf(), getSender(), (IExtendedMessage) message);
	}

	@Override
	public void actorExtendedStart(UntypedActor self, Object... args) {
		extended.actorExtendedStart(self, args);
		LogUtil.debugLog("AvaLongObecj extended actorExtendedStart="+extended.getClass().getName());
	}

	@Override
	public void actorExtendedStop() {
		extended.actorExtendedStop();
	}

	@Override
	public void actorExtendedRestart() {
		extended.actorExtendedRestart();
	}
}
