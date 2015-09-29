package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.LoginPro.*;
import com.zero.example.protobuf.LoginPro.CS_CreateRole;
import com.zero.example.protobuf.LoginPro.CS_CreateRole.Builder;


public class CS_CreateRoleJavaBean implements JavaProtocolTransform {

	private java.lang.String name;
	private java.lang.Integer sex;
	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	public java.lang.Integer getSex()
	{
		return 	sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_CreateRole protocal = (CS_CreateRole) message;
		this.setName(protocal.getName());
		this.setSex(protocal.getSex());
	}

	@Override
	public CS_CreateRole javaBeanToProtocol() {
		Builder newBuilder = CS_CreateRole.newBuilder();
		newBuilder.setName(this.getName());
		newBuilder.setSex(this.getSex());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_CreateRole bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_CreateRole.parseFrom(bytes);
	}
}