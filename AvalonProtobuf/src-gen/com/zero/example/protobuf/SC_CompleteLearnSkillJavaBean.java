package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_CompleteLearnSkill;
import com.zero.example.protobuf.SectPro.SC_CompleteLearnSkill.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_CompleteLearnSkillJavaBean implements JavaProtocolTransform {

	private SkillInfoProtocolJavaBean skillInfo;
	
	
	public SkillInfoProtocolJavaBean getSkillInfo()
	{
		return 	skillInfo;
	}

	public void setSkillInfo(SkillInfoProtocolJavaBean skillInfo) {
		this.skillInfo = skillInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_CompleteLearnSkill protocal = (SC_CompleteLearnSkill) message;
		{
			SkillInfoProtocolJavaBean protocol = new SkillInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getSkillInfo());
			this.setSkillInfo(protocol);
		}
	}

	@Override
	public SC_CompleteLearnSkill javaBeanToProtocol() {
		Builder newBuilder = SC_CompleteLearnSkill.newBuilder();
		{
			SkillInfoProtocol protocol = this.getSkillInfo().javaBeanToProtocol();
			newBuilder.setSkillInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_CompleteLearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_CompleteLearnSkill.parseFrom(bytes);
	}
}