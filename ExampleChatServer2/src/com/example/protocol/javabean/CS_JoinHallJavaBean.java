package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.HallPro.CS_JoinHall;
import com.example.protocol.HallPro.CS_JoinHall.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class CS_JoinHallJavaBean implements JavaProtocolTransform {

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
		CS_JoinHall protocal = (CS_JoinHall) message;
		this.setName(protocal.getName());
	}

	@Override
	public CS_JoinHall javaBeanToProtocol() {
		Builder newBuilder = CS_JoinHall.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_JoinHall bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_JoinHall.parseFrom(bytes);
	}
}