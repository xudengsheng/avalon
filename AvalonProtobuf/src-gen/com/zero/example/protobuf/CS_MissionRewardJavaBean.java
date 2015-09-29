package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.CS_MissionReward;
import com.zero.example.protobuf.MissionPro.CS_MissionReward.Builder;


public class CS_MissionRewardJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_MissionReward protocal = (CS_MissionReward) message;
		this.setMissionId(protocal.getMissionId());
	}

	@Override
	public CS_MissionReward javaBeanToProtocol() {
		Builder newBuilder = CS_MissionReward.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_MissionReward bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_MissionReward.parseFrom(bytes);
	}
}