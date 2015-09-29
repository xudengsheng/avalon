package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ChatPro.*;
import com.zero.example.protobuf.ChatPro.SC_TalkFromWorld;
import com.zero.example.protobuf.ChatPro.SC_TalkFromWorld.Builder;


public class SC_TalkFromWorldJavaBean implements JavaProtocolTransform {

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
		SC_TalkFromWorld protocal = (SC_TalkFromWorld) message;
		this.setChatMessage(protocal.getChatMessage());
	}

	@Override
	public SC_TalkFromWorld javaBeanToProtocol() {
		Builder newBuilder = SC_TalkFromWorld.newBuilder();
		newBuilder.setChatMessage(this.getChatMessage());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_TalkFromWorld bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_TalkFromWorld.parseFrom(bytes);
	}
}