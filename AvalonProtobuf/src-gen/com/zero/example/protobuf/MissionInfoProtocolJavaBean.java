package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.MissionInfoProtocol;
import com.zero.example.protobuf.StructurePro.MissionInfoProtocol.Builder;
import java.util.List;
import java.util.ArrayList;


public class MissionInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	private java.lang.Integer missionState;
	private List<MissionTargetProtocolJavaBean> missionTarget;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	public java.lang.Integer getMissionState()
	{
		return 	missionState;
	}

	public void setMissionState(java.lang.Integer missionState) {
		this.missionState = missionState;
	}	
	public List<MissionTargetProtocolJavaBean> getMissionTarget()
	{
		return 	missionTarget;
	}

	public void setMissionTarget(List<MissionTargetProtocolJavaBean> missionTarget) {
		this.missionTarget = missionTarget;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		MissionInfoProtocol protocal = (MissionInfoProtocol) message;
		this.setMissionId(protocal.getMissionId());
		this.setMissionState(protocal.getMissionState());
		{	
		List<MissionTargetProtocolJavaBean> list = new ArrayList<MissionTargetProtocolJavaBean>();
		for (MissionTargetProtocol JavaBean : protocal.getMissionTargetList()) {
			MissionTargetProtocolJavaBean protocol = new MissionTargetProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setMissionTarget(list);
		}
	}

	@Override
	public MissionInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = MissionInfoProtocol.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		newBuilder.setMissionState(this.getMissionState());
		{
			List<MissionTargetProtocol> list = new ArrayList<MissionTargetProtocol>();
			for (MissionTargetProtocolJavaBean JavaBean : this.getMissionTarget()) {
			list.add((MissionTargetProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllMissionTarget(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public MissionInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return MissionInfoProtocol.parseFrom(bytes);
	}
}