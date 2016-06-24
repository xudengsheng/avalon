package com.example.protocol.javabean;

import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.LoginPro.SC_LoginInfo;
import com.example.protocol.LoginPro.SC_LoginInfo.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_LoginInfoJavaBean implements JavaProtocolTransform {

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
		SC_LoginInfo protocal = (SC_LoginInfo) message;
		this.setName(protocal.getName());
	}

	@Override
	public SC_LoginInfo javaBeanToProtocol() {
		Builder newBuilder = SC_LoginInfo.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_LoginInfo bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_LoginInfo.parseFrom(bytes);
	}
}