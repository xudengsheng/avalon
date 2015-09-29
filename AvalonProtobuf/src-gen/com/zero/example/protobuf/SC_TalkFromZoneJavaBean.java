package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ChatPro.*;
import com.zero.example.protobuf.ChatPro.SC_TalkFromZone;
import com.zero.example.protobuf.ChatPro.SC_TalkFromZone.Builder;


public class SC_TalkFromZoneJavaBean implements JavaProtocolTransform {

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
		SC_TalkFromZone protocal = (SC_TalkFromZone) message;
		this.setChatMessage(protocal.getChatMessage());
	}

	@Override
	public SC_TalkFromZone javaBeanToProtocol() {
		Builder newBuilder = SC_TalkFromZone.newBuilder();
		newBuilder.setChatMessage(this.getChatMessage());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_TalkFromZone bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_TalkFromZone.parseFrom(bytes);
	}
}