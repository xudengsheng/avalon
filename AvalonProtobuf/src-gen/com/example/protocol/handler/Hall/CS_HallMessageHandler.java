package com.example.protocol.handler.Hall;


import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.helper.HallCodecHelper;
import com.example.protocol.javabean.CS_HallMessageJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.SessionLisenter;
import com.zero.example.core.AbstractClientRequestHandler;

public class CS_HallMessageHandler extends AbstractClientRequestHandler {

	@Override
	public JavaProtocolTransform decode(Object message) throws InvalidProtocolBufferException {
		CS_HallMessageJavaBean decodeBean = HallCodecHelper.decodeCS_HallMessageJavaBean((byte[]) message);
		return decodeBean;
	}

	@Override
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		CS_HallMessageJavaBean decodeBean = (CS_HallMessageJavaBean) javaBean;
		//需要校验的数据在这里
		return true;
	}

	@Override
	public void handleClientRequest(Object listener, JavaProtocolTransform message) {
		CS_HallMessageJavaBean decodeBean = (CS_HallMessageJavaBean) message;
		// TODO Auto-generated method stub
		SessionLisenter sessionLisenter=(SessionLisenter) listener;
		//ActorPath actorPath = AppContext.pathCache.get(LoginManager.class.getSimpleName());
		//ActorSelection actorSelection = AppContext.getActorSystem().actorSelection(actorPath);
		//actorSelection.tell(decodeBean, ActorRef.noSender());
	}

}
