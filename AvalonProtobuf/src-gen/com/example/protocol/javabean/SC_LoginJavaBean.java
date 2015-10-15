package com.example.protocol.javabean;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.LoginPro.*;
import com.example.protocol.LoginPro.SC_Login;
import com.example.protocol.LoginPro.SC_Login.Builder;


public class SC_LoginJavaBean implements JavaProtocolTransform {

	private java.lang.Integer stats;
	private java.lang.String name;
	
	public java.lang.Integer getStats()
	{
		return 	stats;
	}

	public void setStats(java.lang.Integer stats) {
		this.stats = stats;
	}	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_Login protocal = (SC_Login) message;
		this.setStats(protocal.getStats());
		this.setName(protocal.getName());
	}

	@Override
	public SC_Login javaBeanToProtocol() {
		Builder newBuilder = SC_Login.newBuilder();
		newBuilder.setStats(this.getStats());
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_Login bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_Login.parseFrom(bytes);
	}
}