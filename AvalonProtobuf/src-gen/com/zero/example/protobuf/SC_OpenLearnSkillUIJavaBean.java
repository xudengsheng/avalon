package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_OpenLearnSkillUI;
import com.zero.example.protobuf.SectPro.SC_OpenLearnSkillUI.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_OpenLearnSkillUIJavaBean implements JavaProtocolTransform {

	private List<SkillInfoProtocolJavaBean> skillInfo;
	
	public List<SkillInfoProtocolJavaBean> getSkillInfo()
	{
		return 	skillInfo;
	}

	public void setSkillInfo(List<SkillInfoProtocolJavaBean> skillInfo) {
		this.skillInfo = skillInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_OpenLearnSkillUI protocal = (SC_OpenLearnSkillUI) message;
		{	
		List<SkillInfoProtocolJavaBean> list = new ArrayList<SkillInfoProtocolJavaBean>();
		for (SkillInfoProtocol JavaBean : protocal.getSkillInfoList()) {
			SkillInfoProtocolJavaBean protocol = new SkillInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setSkillInfo(list);
		}
	}

	@Override
	public SC_OpenLearnSkillUI javaBeanToProtocol() {
		Builder newBuilder = SC_OpenLearnSkillUI.newBuilder();
		{
			List<SkillInfoProtocol> list = new ArrayList<SkillInfoProtocol>();
			for (SkillInfoProtocolJavaBean JavaBean : this.getSkillInfo()) {
			list.add((SkillInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllSkillInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_OpenLearnSkillUI bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_OpenLearnSkillUI.parseFrom(bytes);
	}
}