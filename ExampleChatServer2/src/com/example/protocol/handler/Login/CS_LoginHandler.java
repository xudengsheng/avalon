package com.example.protocol.handler.Login;


import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import com.avalon.api.AppContext;
import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.helper.LoginCodecHelper;
import com.example.protocol.javabean.CS_LoginJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.SessionLisenter;
import com.zero.example.core.AbstractClientRequestHandler;
import com.zero.example.login.LoginManager;
import com.zero.example.message.LoginManagerMessage.LoginMessage;

public class CS_LoginHandler extends AbstractClientRequestHandler {

	@Override
	public JavaProtocolTransform decode(Object message) throws InvalidProtocolBufferException {
		CS_LoginJavaBean decodeBean = LoginCodecHelper.decodeCS_LoginJavaBean((byte[]) message);
		return decodeBean;
	}

	@Override
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		CS_LoginJavaBean decodeBean = (CS_LoginJavaBean) javaBean;
		//需要校验的数据在这里
		return true;
	}

	@Override
	public void handleClientRequest(Object listener, JavaProtocolTransform message) {
		CS_LoginJavaBean decodeBean = (CS_LoginJavaBean) message;
		// TODO Auto-generated method stub
		SessionLisenter sessionLisenter=(SessionLisenter) listener;
		ActorPath actorPath = AppContext.pathCache.get(LoginManager.class.getSimpleName());
		LoginMessage loginMessage=new LoginMessage(decodeBean, sessionLisenter);
		ActorSelection actorSelection = AppContext.getActorSystem().actorSelection(actorPath);
		actorSelection.tell(loginMessage, ActorRef.noSender());
	}

}
