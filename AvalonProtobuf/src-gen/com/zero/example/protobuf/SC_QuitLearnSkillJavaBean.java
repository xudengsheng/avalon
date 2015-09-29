package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_QuitLearnSkill;
import com.zero.example.protobuf.SectPro.SC_QuitLearnSkill.Builder;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;


public class SC_QuitLearnSkillJavaBean implements JavaProtocolTransform {

	private SkillInfoProtocolJavaBean skillInfo;
	private LearnSkillConsumeInfoProtocolJavaBean consumeInfo;
	
	
	public SkillInfoProtocolJavaBean getSkillInfo()
	{
		return 	skillInfo;
	}

	public void setSkillInfo(SkillInfoProtocolJavaBean skillInfo) {
		this.skillInfo = skillInfo;
	}	
	
	
	public LearnSkillConsumeInfoProtocolJavaBean getConsumeInfo()
	{
		return 	consumeInfo;
	}

	public void setConsumeInfo(LearnSkillConsumeInfoProtocolJavaBean consumeInfo) {
		this.consumeInfo = consumeInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_QuitLearnSkill protocal = (SC_QuitLearnSkill) message;
		{
			SkillInfoProtocolJavaBean protocol = new SkillInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getSkillInfo());
			this.setSkillInfo(protocol);
		}
		{
			LearnSkillConsumeInfoProtocolJavaBean protocol = new LearnSkillConsumeInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getConsumeInfo());
			this.setConsumeInfo(protocol);
		}
	}

	@Override
	public SC_QuitLearnSkill javaBeanToProtocol() {
		Builder newBuilder = SC_QuitLearnSkill.newBuilder();
		{
			SkillInfoProtocol protocol = this.getSkillInfo().javaBeanToProtocol();
			newBuilder.setSkillInfo(protocol);
		}
		{
			LearnSkillConsumeInfoProtocol protocol = this.getConsumeInfo().javaBeanToProtocol();
			newBuilder.setConsumeInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_QuitLearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_QuitLearnSkill.parseFrom(bytes);
	}
}