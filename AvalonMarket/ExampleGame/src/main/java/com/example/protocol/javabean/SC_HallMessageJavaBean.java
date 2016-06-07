package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.HallPro.SC_HallMessage;
import com.example.protocol.HallPro.SC_HallMessage.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_HallMessageJavaBean implements JavaProtocolTransform {

	private java.lang.String usersName;
	private java.lang.String context;
	
	public java.lang.String getUsersName()
	{
		return 	usersName;
	}

	public void setUsersName(java.lang.String usersName) {
		this.usersName = usersName;
	}	
	public java.lang.String getContext()
	{
		return 	context;
	}

	public void setContext(java.lang.String context) {
		this.context = context;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_HallMessage protocal = (SC_HallMessage) message;
		this.setUsersName(protocal.getUsersName());
		this.setContext(protocal.getContext());
	}

	@Override
	public SC_HallMessage javaBeanToProtocol() {
		Builder newBuilder = SC_HallMessage.newBuilder();
		newBuilder.setUsersName(this.getUsersName());
		newBuilder.setContext(this.getContext());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_HallMessage bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_HallMessage.parseFrom(bytes);
	}
}