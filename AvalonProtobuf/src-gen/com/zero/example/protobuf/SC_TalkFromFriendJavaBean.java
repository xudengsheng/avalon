package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ChatPro.*;
import com.zero.example.protobuf.ChatPro.SC_TalkFromFriend;
import com.zero.example.protobuf.ChatPro.SC_TalkFromFriend.Builder;


public class SC_TalkFromFriendJavaBean implements JavaProtocolTransform {

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
		SC_TalkFromFriend protocal = (SC_TalkFromFriend) message;
		this.setFriendId(protocal.getFriendId());
		this.setChatMessage(protocal.getChatMessage());
	}

	@Override
	public SC_TalkFromFriend javaBeanToProtocol() {
		Builder newBuilder = SC_TalkFromFriend.newBuilder();
		newBuilder.setFriendId(this.getFriendId());
		newBuilder.setChatMessage(this.getChatMessage());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_TalkFromFriend bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_TalkFromFriend.parseFrom(bytes);
	}
}