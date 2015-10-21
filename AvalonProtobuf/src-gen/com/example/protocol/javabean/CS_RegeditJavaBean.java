package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.LoginPro.CS_Regedit;
import com.example.protocol.LoginPro.CS_Regedit.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class CS_RegeditJavaBean implements JavaProtocolTransform {

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
		CS_Regedit protocal = (CS_Regedit) message;
		this.setName(protocal.getName());
		this.setPassword(protocal.getPassword());
	}

	@Override
	public CS_Regedit javaBeanToProtocol() {
		Builder newBuilder = CS_Regedit.newBuilder();
		newBuilder.setName(this.getName());
		newBuilder.setPassword(this.getPassword());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_Regedit bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_Regedit.parseFrom(bytes);
	}
}