package com.example.protocol.javabean;

import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.LoginPro.CS_LoginNewName;
import com.example.protocol.LoginPro.CS_LoginNewName.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class CS_LoginNewNameJavaBean implements JavaProtocolTransform {

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
		CS_LoginNewName protocal = (CS_LoginNewName) message;
		this.setName(protocal.getName());
	}

	@Override
	public CS_LoginNewName javaBeanToProtocol() {
		Builder newBuilder = CS_LoginNewName.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_LoginNewName bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_LoginNewName.parseFrom(bytes);
	}
}