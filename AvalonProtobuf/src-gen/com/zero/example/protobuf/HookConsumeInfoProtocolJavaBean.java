package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.HookConsumeInfoProtocol;
import com.zero.example.protobuf.StructurePro.HookConsumeInfoProtocol.Builder;


public class HookConsumeInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer consumeType;
	private java.lang.Integer consumeValue;
	
	public java.lang.Integer getConsumeType()
	{
		return 	consumeType;
	}

	public void setConsumeType(java.lang.Integer consumeType) {
		this.consumeType = consumeType;
	}	
	public java.lang.Integer getConsumeValue()
	{
		return 	consumeValue;
	}

	public void setConsumeValue(java.lang.Integer consumeValue) {
		this.consumeValue = consumeValue;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		HookConsumeInfoProtocol protocal = (HookConsumeInfoProtocol) message;
		this.setConsumeType(protocal.getConsumeType());
		this.setConsumeValue(protocal.getConsumeValue());
	}

	@Override
	public HookConsumeInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = HookConsumeInfoProtocol.newBuilder();
		newBuilder.setConsumeType(this.getConsumeType());
		newBuilder.setConsumeValue(this.getConsumeValue());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public HookConsumeInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return HookConsumeInfoProtocol.parseFrom(bytes);
	}
}