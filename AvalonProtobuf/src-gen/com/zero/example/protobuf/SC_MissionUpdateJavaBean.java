package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_MissionUpdate;
import com.zero.example.protobuf.MissionPro.SC_MissionUpdate.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_MissionUpdateJavaBean implements JavaProtocolTransform {

	private java.lang.Integer missionId;
	private List<MissionTargetProtocolJavaBean> missionTarget;
	private java.lang.Integer state;
	
	public java.lang.Integer getMissionId()
	{
		return 	missionId;
	}

	public void setMissionId(java.lang.Integer missionId) {
		this.missionId = missionId;
	}	
	public List<MissionTargetProtocolJavaBean> getMissionTarget()
	{
		return 	missionTarget;
	}

	public void setMissionTarget(List<MissionTargetProtocolJavaBean> missionTarget) {
		this.missionTarget = missionTarget;
	}	
	public java.lang.Integer getState()
	{
		return 	state;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_MissionUpdate protocal = (SC_MissionUpdate) message;
		this.setMissionId(protocal.getMissionId());
		{	
		List<MissionTargetProtocolJavaBean> list = new ArrayList<MissionTargetProtocolJavaBean>();
		for (MissionTargetProtocol JavaBean : protocal.getMissionTargetList()) {
			MissionTargetProtocolJavaBean protocol = new MissionTargetProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setMissionTarget(list);
		}
		this.setState(protocal.getState());
	}

	@Override
	public SC_MissionUpdate javaBeanToProtocol() {
		Builder newBuilder = SC_MissionUpdate.newBuilder();
		newBuilder.setMissionId(this.getMissionId());
		{
			List<MissionTargetProtocol> list = new ArrayList<MissionTargetProtocol>();
			for (MissionTargetProtocolJavaBean JavaBean : this.getMissionTarget()) {
			list.add((MissionTargetProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllMissionTarget(list);
		}
		newBuilder.setState(this.getState());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_MissionUpdate bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_MissionUpdate.parseFrom(bytes);
	}
}