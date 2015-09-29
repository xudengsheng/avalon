package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.LoginPro.*;
import com.zero.example.protobuf.LoginPro.CS_GuestLogin;
import com.zero.example.protobuf.LoginPro.CS_GuestLogin.Builder;


public class CS_GuestLoginJavaBean implements JavaProtocolTransform {

	private java.lang.String name;
	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_GuestLogin protocal = (CS_GuestLogin) message;
		this.setName(protocal.getName());
	}

	@Override
	public CS_GuestLogin javaBeanToProtocol() {
		Builder newBuilder = CS_GuestLogin.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_GuestLogin bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_GuestLogin.parseFrom(bytes);
	}
}