package com.example.protocol.handler.Hall;


import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.helper.HallCodecHelper;
import com.example.protocol.javabean.CS_JoinHallJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.SessionLisenter;
import com.zero.example.core.AbstractClientRequestHandler;

public class CS_JoinHallHandler extends AbstractClientRequestHandler {

	@Override
	public JavaProtocolTransform decode(Object message) throws InvalidProtocolBufferException {
		CS_JoinHallJavaBean decodeBean = HallCodecHelper.decodeCS_JoinHallJavaBean((byte[]) message);
		return decodeBean;
	}

	@Override
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		CS_JoinHallJavaBean decodeBean = (CS_JoinHallJavaBean) javaBean;
		//需要校验的数据在这里
		return true;
	}

	@Override
	public void handleClientRequest(Object listener, JavaProtocolTransform message) {
		CS_JoinHallJavaBean decodeBean = (CS_JoinHallJavaBean) message;
		// TODO Auto-generated method stub
		SessionLisenter sessionLisenter=(SessionLisenter) listener;
		//ActorPath actorPath = AppContext.pathCache.get(LoginManager.class.getSimpleName());
		//ActorSelection actorSelection = AppContext.getActorSystem().actorSelection(actorPath);
		//actorSelection.tell(decodeBean, ActorRef.noSender());
	}

}
