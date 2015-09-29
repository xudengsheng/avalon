package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.MissionTargetProtocol;
import com.zero.example.protobuf.StructurePro.MissionTargetProtocol.Builder;


public class MissionTargetProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer targetId;
	private java.lang.Integer count;
	
	public java.lang.Integer getTargetId()
	{
		return 	targetId;
	}

	public void setTargetId(java.lang.Integer targetId) {
		this.targetId = targetId;
	}	
	public java.lang.Integer getCount()
	{
		return 	count;
	}

	public void setCount(java.lang.Integer count) {
		this.count = count;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		MissionTargetProtocol protocal = (MissionTargetProtocol) message;
		this.setTargetId(protocal.getTargetId());
		this.setCount(protocal.getCount());
	}

	@Override
	public MissionTargetProtocol javaBeanToProtocol() {
		Builder newBuilder = MissionTargetProtocol.newBuilder();
		newBuilder.setTargetId(this.getTargetId());
		newBuilder.setCount(this.getCount());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public MissionTargetProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return MissionTargetProtocol.parseFrom(bytes);
	}
}