package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.HookRewardInfoProtocol;
import com.zero.example.protobuf.StructurePro.HookRewardInfoProtocol.Builder;


public class HookRewardInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer rewardType;
	private java.lang.Integer rewardValue;
	
	public java.lang.Integer getRewardType()
	{
		return 	rewardType;
	}

	public void setRewardType(java.lang.Integer rewardType) {
		this.rewardType = rewardType;
	}	
	public java.lang.Integer getRewardValue()
	{
		return 	rewardValue;
	}

	public void setRewardValue(java.lang.Integer rewardValue) {
		this.rewardValue = rewardValue;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		HookRewardInfoProtocol protocal = (HookRewardInfoProtocol) message;
		this.setRewardType(protocal.getRewardType());
		this.setRewardValue(protocal.getRewardValue());
	}

	@Override
	public HookRewardInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = HookRewardInfoProtocol.newBuilder();
		newBuilder.setRewardType(this.getRewardType());
		newBuilder.setRewardValue(this.getRewardValue());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public HookRewardInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return HookRewardInfoProtocol.parseFrom(bytes);
	}
}