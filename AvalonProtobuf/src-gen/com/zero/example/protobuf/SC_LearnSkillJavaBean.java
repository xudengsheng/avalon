package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_LearnSkill;
import com.zero.example.protobuf.SectPro.SC_LearnSkill.Builder;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;


public class SC_LearnSkillJavaBean implements JavaProtocolTransform {

	private java.lang.Integer currentTimestamp;
	private java.lang.Integer needTime_s;
	private SkillInfoProtocolJavaBean skillInfo;
	private LearnSkillConsumeInfoProtocolJavaBean consumeInfo;
	
	public java.lang.Integer getCurrentTimestamp()
	{
		return 	currentTimestamp;
	}

	public void setCurrentTimestamp(java.lang.Integer currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}	
	public java.lang.Integer getNeedTime_s()
	{
		return 	needTime_s;
	}

	public void setNeedTime_s(java.lang.Integer needTime_s) {
		this.needTime_s = needTime_s;
	}	
	
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
		SC_LearnSkill protocal = (SC_LearnSkill) message;
		this.setCurrentTimestamp(protocal.getCurrentTimestamp());
		this.setNeedTime_s(protocal.getNeedTimeS());
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
	public SC_LearnSkill javaBeanToProtocol() {
		Builder newBuilder = SC_LearnSkill.newBuilder();
		newBuilder.setCurrentTimestamp(this.getCurrentTimestamp());
		newBuilder.setNeedTimeS(this.getNeedTime_s());
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
	public SC_LearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_LearnSkill.parseFrom(bytes);
	}
}