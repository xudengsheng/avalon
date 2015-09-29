package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.UpdatePlayerPro.*;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdateUserSkillGroupInfo;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdateUserSkillGroupInfo.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_UpdateUserSkillGroupInfoJavaBean implements JavaProtocolTransform {

	private List<SkillGroupInfoProtocolJavaBean> skillGroupInfo;
	
	public List<SkillGroupInfoProtocolJavaBean> getSkillGroupInfo()
	{
		return 	skillGroupInfo;
	}

	public void setSkillGroupInfo(List<SkillGroupInfoProtocolJavaBean> skillGroupInfo) {
		this.skillGroupInfo = skillGroupInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_UpdateUserSkillGroupInfo protocal = (SC_UpdateUserSkillGroupInfo) message;
		{	
		List<SkillGroupInfoProtocolJavaBean> list = new ArrayList<SkillGroupInfoProtocolJavaBean>();
		for (SkillGroupInfoProtocol JavaBean : protocal.getSkillGroupInfoList()) {
			SkillGroupInfoProtocolJavaBean protocol = new SkillGroupInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setSkillGroupInfo(list);
		}
	}

	@Override
	public SC_UpdateUserSkillGroupInfo javaBeanToProtocol() {
		Builder newBuilder = SC_UpdateUserSkillGroupInfo.newBuilder();
		{
			List<SkillGroupInfoProtocol> list = new ArrayList<SkillGroupInfoProtocol>();
			for (SkillGroupInfoProtocolJavaBean JavaBean : this.getSkillGroupInfo()) {
			list.add((SkillGroupInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllSkillGroupInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_UpdateUserSkillGroupInfo bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_UpdateUserSkillGroupInfo.parseFrom(bytes);
	}
}