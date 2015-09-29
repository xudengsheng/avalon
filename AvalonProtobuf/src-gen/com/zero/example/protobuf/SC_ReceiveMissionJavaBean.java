package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_ReceiveMission;
import com.zero.example.protobuf.MissionPro.SC_ReceiveMission.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_ReceiveMissionJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	private java.lang.Boolean canReceive;
	private List<MissionTargetProtocolJavaBean> missionTarget;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	public java.lang.Boolean getCanReceive()
	{
		return 	canReceive;
	}

	public void setCanReceive(java.lang.Boolean canReceive) {
		this.canReceive = canReceive;
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
		SC_ReceiveMission protocal = (SC_ReceiveMission) message;
		this.setMissionId(protocal.getMissionId());
		this.setCanReceive(protocal.getCanReceive());
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
	public SC_ReceiveMission javaBeanToProtocol() {
		Builder newBuilder = SC_ReceiveMission.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		newBuilder.setCanReceive(this.getCanReceive());
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
	public SC_ReceiveMission bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_ReceiveMission.parseFrom(bytes);
	}
}