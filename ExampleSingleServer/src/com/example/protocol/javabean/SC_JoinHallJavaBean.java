package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.HallPro.SC_JoinHall;
import com.example.protocol.HallPro.SC_JoinHall.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_JoinHallJavaBean implements JavaProtocolTransform {

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
		SC_JoinHall protocal = (SC_JoinHall) message;
		this.setName(protocal.getName());
	}

	@Override
	public SC_JoinHall javaBeanToProtocol() {
		Builder newBuilder = SC_JoinHall.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_JoinHall bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_JoinHall.parseFrom(bytes);
	}
}