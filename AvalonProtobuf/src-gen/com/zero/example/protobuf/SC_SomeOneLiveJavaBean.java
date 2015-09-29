package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.SC_SomeOneLive;
import com.zero.example.protobuf.BattlePro.SC_SomeOneLive.Builder;


public class SC_SomeOneLiveJavaBean implements JavaProtocolTransform {

	private java.lang.Long soneId;
	
	public java.lang.Long getSoneId()
	{
		return 	soneId;
	}

	public void setSoneId(java.lang.Long soneId) {
		this.soneId = soneId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_SomeOneLive protocal = (SC_SomeOneLive) message;
		this.setSoneId(protocal.getSoneId());
	}

	@Override
	public SC_SomeOneLive javaBeanToProtocol() {
		Builder newBuilder = SC_SomeOneLive.newBuilder();
		newBuilder.setSoneId(this.getSoneId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_SomeOneLive bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_SomeOneLive.parseFrom(bytes);
	}
}