package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.UpdatePlayerPro.*;
import com.zero.example.protobuf.UpdatePlayerPro.SC_LevelUp;
import com.zero.example.protobuf.UpdatePlayerPro.SC_LevelUp.Builder;


public class SC_LevelUpJavaBean implements JavaProtocolTransform {

	private java.lang.Integer userLevel;
	
	public java.lang.Integer getUserLevel()
	{
		return 	userLevel;
	}

	public void setUserLevel(java.lang.Integer userLevel) {
		this.userLevel = userLevel;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_LevelUp protocal = (SC_LevelUp) message;
		this.setUserLevel(protocal.getUserLevel());
	}

	@Override
	public SC_LevelUp javaBeanToProtocol() {
		Builder newBuilder = SC_LevelUp.newBuilder();
		newBuilder.setUserLevel(this.getUserLevel());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_LevelUp bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_LevelUp.parseFrom(bytes);
	}
}