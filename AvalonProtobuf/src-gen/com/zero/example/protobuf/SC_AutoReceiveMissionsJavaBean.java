package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_AutoReceiveMissions;
import com.zero.example.protobuf.MissionPro.SC_AutoReceiveMissions.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_AutoReceiveMissionsJavaBean implements JavaProtocolTransform {

	private List<MissionInfoProtocolJavaBean> missionInfo;
	
	public List<MissionInfoProtocolJavaBean> getMissionInfo()
	{
		return 	missionInfo;
	}

	public void setMissionInfo(List<MissionInfoProtocolJavaBean> missionInfo) {
		this.missionInfo = missionInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_AutoReceiveMissions protocal = (SC_AutoReceiveMissions) message;
		{	
		List<MissionInfoProtocolJavaBean> list = new ArrayList<MissionInfoProtocolJavaBean>();
		for (MissionInfoProtocol JavaBean : protocal.getMissionInfoList()) {
			MissionInfoProtocolJavaBean protocol = new MissionInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setMissionInfo(list);
		}
	}

	@Override
	public SC_AutoReceiveMissions javaBeanToProtocol() {
		Builder newBuilder = SC_AutoReceiveMissions.newBuilder();
		{
			List<MissionInfoProtocol> list = new ArrayList<MissionInfoProtocol>();
			for (MissionInfoProtocolJavaBean JavaBean : this.getMissionInfo()) {
			list.add((MissionInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllMissionInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_AutoReceiveMissions bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_AutoReceiveMissions.parseFrom(bytes);
	}
}