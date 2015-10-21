package com.zero.example;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;

import com.avalon.api.ActorSession;
import com.avalon.api.AppContext;
import com.avalon.api.ClientSessionLinenter;
import com.avalon.api.internal.IoMessage;
import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
import com.example.protocol.MessageKey;
import com.example.protocol.javabean.SC_Globle_messageJavaBean;
import com.zero.example.actor.message.UserMessage.UserHallMessage;
import com.zero.example.actor.message.UserMessage.UserLeaveHall;
import com.zero.example.core.ExampleClientExtension;
import com.zero.example.hall.message.UserJoinHallBack;
import com.zero.example.hall.message.UserLeaveHallBack;
import com.zero.example.hall.message.UserMessageHallBack;
import com.zero.example.login.message.LoginBackMessage;
import com.zero.example.login.message.LoginRegeditBackMessage;

/**
 * 客户端操作类
 * 
 * @author zero
 *
 */
public class SessionLisenter implements ClientSessionLinenter {

	UntypedActor selfUntypedActor;
	UntypedActorContext context;
	ActorRef self;
	ActorSession clientSession;

	ActorRef user;

	@Override
	public void receivedMessage(ActorSession clientSession, IoMessagePackage message) {
		// 收到网络协议的
		this.clientSession = clientSession;
		selfUntypedActor = clientSession.getSelfUntypedActor();
		context = selfUntypedActor.getContext();
		self = selfUntypedActor.getSelf();

		int opCode = ((IoMessagePackage) message).getOpCode();
		byte[] rawData = ((IoMessagePackage) message).getRawData();

		ExampleClientExtension clientExtension = AppContext.getManager(ExampleClientExtension.class);
		clientExtension.handleClientRequest(this, opCode, rawData);
	}

	@Override
	public void receivedActorMessage(ActorRef sender, IoMessage ioMessage) {
		// 收到其他Actor的协议
		if (ioMessage instanceof LoginRegeditBackMessage) {
			if (((LoginRegeditBackMessage) ioMessage).success) {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(2);
				bean.setContext("注册成功");
				bean.setStats(1);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				clientSession.sendIoMessage(ioMessagePackage);
			} else {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(2);
				bean.setContext("注册失败");
				bean.setStats(0);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				clientSession.sendIoMessage(ioMessagePackage);
			}

		} else if (ioMessage instanceof LoginBackMessage) {
			if (((LoginBackMessage) ioMessage).state == 1) {
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_HallInfo,
						((LoginBackMessage) ioMessage).bs);
				clientSession.sendIoMessage(ioMessagePackage);
				this.user = sender;
			} else {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(4);
				bean.setContext("登入");
				bean.setStats(((LoginBackMessage) ioMessage).state);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				clientSession.sendIoMessage(ioMessagePackage);
			}
		} else if (ioMessage instanceof UserJoinHallBack) {
			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_JoinHall,
					((UserJoinHallBack) ioMessage).bs);
			clientSession.sendIoMessage(ioMessagePackage);
		} else if (ioMessage instanceof UserLeaveHallBack) {
			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_LeaveHall,
					((UserLeaveHallBack) ioMessage).bs);
			clientSession.sendIoMessage(ioMessagePackage);
		} else if (ioMessage instanceof UserMessageHallBack) {
			IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_HallMessage,
					((UserMessageHallBack) ioMessage).bs);
			clientSession.sendIoMessage(ioMessagePackage);
		}
	}

	@Override
	public void disconnected(boolean graceful) {
		// TODO 失去网络连接的
		if (user != null) {
			user.tell(PoisonPill.getInstance(), ActorRef.noSender());
		}
	}

	public UntypedActor getSelfUntypedActor() {
		return selfUntypedActor;
	}

	public void setSelfUntypedActor(UntypedActor selfUntypedActor) {
		this.selfUntypedActor = selfUntypedActor;
	}

	public UntypedActorContext getContext() {
		return context;
	}

	public void setContext(UntypedActorContext context) {
		this.context = context;
	}

	public ActorRef getSelf() {
		return self;
	}

	public void setSelf(ActorRef self) {
		this.self = self;
	}

}
