package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_MissionReward;
import com.zero.example.protobuf.MissionPro.SC_MissionReward.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_MissionRewardJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	private java.lang.Boolean canGetReward;
	private MissionRewardInfoProtocolJavaBean missonRewardInfo;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	public java.lang.Boolean getCanGetReward()
	{
		return 	canGetReward;
	}

	public void setCanGetReward(java.lang.Boolean canGetReward) {
		this.canGetReward = canGetReward;
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
		SC_MissionReward protocal = (SC_MissionReward) message;
		this.setMissionId(protocal.getMissionId());
		this.setCanGetReward(protocal.getCanGetReward());
		{
			MissionRewardInfoProtocolJavaBean protocol = new MissionRewardInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getMissonRewardInfo());
			this.setMissonRewardInfo(protocol);
		}
	}

	@Override
	public SC_MissionReward javaBeanToProtocol() {
		Builder newBuilder = SC_MissionReward.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		newBuilder.setCanGetReward(this.getCanGetReward());
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
	public SC_MissionReward bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_MissionReward.parseFrom(bytes);
	}
}