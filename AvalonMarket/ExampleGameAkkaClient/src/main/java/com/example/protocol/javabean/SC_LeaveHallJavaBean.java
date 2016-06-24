package com.example.protocol.javabean;

import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.HallPro.SC_LeaveHall;
import com.example.protocol.HallPro.SC_LeaveHall.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_LeaveHallJavaBean implements JavaProtocolTransform {

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
		SC_LeaveHall protocal = (SC_LeaveHall) message;
		this.setName(protocal.getName());
	}

	@Override
	public SC_LeaveHall javaBeanToProtocol() {
		Builder newBuilder = SC_LeaveHall.newBuilder();
		newBuilder.setName(this.getName());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_LeaveHall bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_LeaveHall.parseFrom(bytes);
	}
}