package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.MissionRewardInfoProtocol;
import com.zero.example.protobuf.StructurePro.MissionRewardInfoProtocol.Builder;


public class MissionRewardInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer rewardMoney;
	private java.lang.Integer rewardExp;
	
	public java.lang.Integer getRewardMoney()
	{
		return 	rewardMoney;
	}

	public void setRewardMoney(java.lang.Integer rewardMoney) {
		this.rewardMoney = rewardMoney;
	}	
	public java.lang.Integer getRewardExp()
	{
		return 	rewardExp;
	}

	public void setRewardExp(java.lang.Integer rewardExp) {
		this.rewardExp = rewardExp;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		MissionRewardInfoProtocol protocal = (MissionRewardInfoProtocol) message;
		this.setRewardMoney(protocal.getRewardMoney());
		this.setRewardExp(protocal.getRewardExp());
	}

	@Override
	public MissionRewardInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = MissionRewardInfoProtocol.newBuilder();
		newBuilder.setRewardMoney(this.getRewardMoney());
		newBuilder.setRewardExp(this.getRewardExp());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public MissionRewardInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return MissionRewardInfoProtocol.parseFrom(bytes);
	}
}