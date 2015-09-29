package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ChatPro.*;
import com.zero.example.protobuf.ChatPro.CS_TalkToFriend;
import com.zero.example.protobuf.ChatPro.CS_TalkToFriend.Builder;


public class CS_TalkToFriendJavaBean implements JavaProtocolTransform {

	private java.lang.Long friendId;
	private java.lang.String chatMessage;
	
	public java.lang.Long getFriendId()
	{
		return 	friendId;
	}

	public void setFriendId(java.lang.Long friendId) {
		this.friendId = friendId;
	}	
	public java.lang.String getChatMessage()
	{
		return 	chatMessage;
	}

	public void setChatMessage(java.lang.String chatMessage) {
		this.chatMessage = chatMessage;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_TalkToFriend protocal = (CS_TalkToFriend) message;
		this.setFriendId(protocal.getFriendId());
		this.setChatMessage(protocal.getChatMessage());
	}

	@Override
	public CS_TalkToFriend javaBeanToProtocol() {
		Builder newBuilder = CS_TalkToFriend.newBuilder();
		newBuilder.setFriendId(this.getFriendId());
		newBuilder.setChatMessage(this.getChatMessage());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_TalkToFriend bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_TalkToFriend.parseFrom(bytes);
	}
}