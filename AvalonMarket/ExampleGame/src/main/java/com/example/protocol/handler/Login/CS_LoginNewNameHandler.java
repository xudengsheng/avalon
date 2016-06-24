package com.example.protocol.handler.Login;


import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.helper.LoginCodecHelper;
import com.example.protocol.javabean.CS_LoginNewNameJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.SessionLisenter;
import com.zero.example.core.AbstractClientRequestHandler;

public class CS_LoginNewNameHandler extends AbstractClientRequestHandler {

	@Override
	public JavaProtocolTransform decode(Object message) throws InvalidProtocolBufferException {
		CS_LoginNewNameJavaBean decodeBean = LoginCodecHelper.decodeCS_LoginNewNameJavaBean((byte[]) message);
		return decodeBean;
	}

	@Override
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		CS_LoginNewNameJavaBean decodeBean = (CS_LoginNewNameJavaBean) javaBean;
		//需要校验的数据在这里
		return true;
	}

	@Override
	public void handleClientRequest(Object listener, JavaProtocolTransform message) {
		CS_LoginNewNameJavaBean decodeBean = (CS_LoginNewNameJavaBean) message;
		// TODO Auto-generated method stub
		SessionLisenter sessionLisenter=(SessionLisenter) listener;
		//ActorPath actorPath = AppContext.pathCache.get(LoginManager.class.getSimpleName());
		//ActorSelection actorSelection = AppContext.getActorSystem().actorSelection(actorPath);
		//actorSelection.tell(decodeBean, ActorRef.noSender());
	}

}
