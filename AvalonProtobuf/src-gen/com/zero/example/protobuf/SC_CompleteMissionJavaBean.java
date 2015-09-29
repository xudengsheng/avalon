package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_CompleteMission;
import com.zero.example.protobuf.MissionPro.SC_CompleteMission.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_CompleteMissionJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	private java.lang.Boolean canComplete;
	private MissionRewardInfoProtocolJavaBean missonRewardInfo;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	public java.lang.Boolean getCanComplete()
	{
		return 	canComplete;
	}

	public void setCanComplete(java.lang.Boolean canComplete) {
		this.canComplete = canComplete;
	}	
	
	public MissionRewardInfoProtocolJavaBean getMissonRewardInfo()
	{
		return 	missonRewardInfo;
	}

	public void setMissonRewardInfo(MissionRewardInfoProtocolJavaBean missonRewardInfo) {
		this.missonRewardInfo = missonRewardInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_CompleteMission protocal = (SC_CompleteMission) message;
		this.setMissionId(protocal.getMissionId());
		this.setCanComplete(protocal.getCanComplete());
		{
			MissionRewardInfoProtocolJavaBean protocol = new MissionRewardInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getMissonRewardInfo());
			this.setMissonRewardInfo(protocol);
		}
	}

	@Override
	public SC_CompleteMission javaBeanToProtocol() {
		Builder newBuilder = SC_CompleteMission.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		newBuilder.setCanComplete(this.getCanComplete());
		{
			MissionRewardInfoProtocol protocol = this.getMissonRewardInfo().javaBeanToProtocol();
			newBuilder.setMissonRewardInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_CompleteMission bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_CompleteMission.parseFrom(bytes);
	}
}