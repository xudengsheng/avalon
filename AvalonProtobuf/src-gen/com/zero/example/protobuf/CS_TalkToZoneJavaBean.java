package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ChatPro.*;
import com.zero.example.protobuf.ChatPro.CS_TalkToZone;
import com.zero.example.protobuf.ChatPro.CS_TalkToZone.Builder;


public class CS_TalkToZoneJavaBean implements JavaProtocolTransform {

	private java.lang.String chatMessage;
	
	public java.lang.String getChatMessage()
	{
		return 	chatMessage;
	}

	public void setChatMessage(java.lang.String chatMessage) {
		this.chatMessage = chatMessage;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_TalkToZone protocal = (CS_TalkToZone) message;
		this.setChatMessage(protocal.getChatMessage());
	}

	@Override
	public CS_TalkToZone javaBeanToProtocol() {
		Builder newBuilder = CS_TalkToZone.newBuilder();
		newBuilder.setChatMessage(this.getChatMessage());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_TalkToZone bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_TalkToZone.parseFrom(bytes);
	}
}