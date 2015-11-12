package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.LoginPro.CS_Login;
import com.example.protocol.LoginPro.CS_Login.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class CS_LoginJavaBean implements JavaProtocolTransform {

	private java.lang.String name;
	private java.lang.String password;
	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	public java.lang.String getPassword()
	{
		return 	password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_Login protocal = (CS_Login) message;
		this.setName(protocal.getName());
		this.setPassword(protocal.getPassword());
	}

	@Override
	public CS_Login javaBeanToProtocol() {
		Builder newBuilder = CS_Login.newBuilder();
		newBuilder.setName(this.getName());
		newBuilder.setPassword(this.getPassword());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_Login bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_Login.parseFrom(bytes);
	}
}