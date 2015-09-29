package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.CS_ReceiveMission;
import com.zero.example.protobuf.MissionPro.CS_ReceiveMission.Builder;


public class CS_ReceiveMissionJavaBean implements JavaProtocolTransform {

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
		CS_ReceiveMission protocal = (CS_ReceiveMission) message;
		this.setMissionId(protocal.getMissionId());
	}

	@Override
	public CS_ReceiveMission javaBeanToProtocol() {
		Builder newBuilder = CS_ReceiveMission.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_ReceiveMission bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_ReceiveMission.parseFrom(bytes);
	}
}