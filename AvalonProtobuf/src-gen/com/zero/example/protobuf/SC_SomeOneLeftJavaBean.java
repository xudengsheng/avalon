package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.SC_SomeOneLeft;
import com.zero.example.protobuf.ScenePro.SC_SomeOneLeft.Builder;


public class SC_SomeOneLeftJavaBean implements JavaProtocolTransform {

	private java.lang.Long uid;
	
	public java.lang.Long getUid()
	{
		return 	uid;
	}

	public void setUid(java.lang.Long uid) {
		this.uid = uid;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_SomeOneLeft protocal = (SC_SomeOneLeft) message;
		this.setUid(protocal.getUid());
	}

	@Override
	public SC_SomeOneLeft javaBeanToProtocol() {
		Builder newBuilder = SC_SomeOneLeft.newBuilder();
		newBuilder.setUid(this.getUid());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_SomeOneLeft bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_SomeOneLeft.parseFrom(bytes);
	}
}