package com.example.protocol.handler.Hall;


import akka.actor.ActorRef;

import com.avalon.gameengine.InstanceWorld;
import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.helper.HallCodecHelper;
import com.example.protocol.javabean.CS_HallMessageJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.SessionLisenter;
import com.zero.example.core.AbstractClientRequestHandler;
import com.zero.example.message.WorldMessage;

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
		WorldMessage worldMessage=new WorldMessage.HallMessage(decodeBean);
		InstanceWorld.worldRef.tell(worldMessage, ActorRef.noSender());
	}

}
