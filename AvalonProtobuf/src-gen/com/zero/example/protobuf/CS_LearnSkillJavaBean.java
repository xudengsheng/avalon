package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_LearnSkill;
import com.zero.example.protobuf.SectPro.CS_LearnSkill.Builder;


public class CS_LearnSkillJavaBean implements JavaProtocolTransform {

	private java.lang.Integer skillGroupId;
	private java.lang.Integer consumePotency;
	
	public java.lang.Integer getSkillGroupId()
	{
		return 	skillGroupId;
	}

	public void setSkillGroupId(java.lang.Integer skillGroupId) {
		this.skillGroupId = skillGroupId;
	}	
	public java.lang.Integer getConsumePotency()
	{
		return 	consumePotency;
	}

	public void setConsumePotency(java.lang.Integer consumePotency) {
		this.consumePotency = consumePotency;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_LearnSkill protocal = (CS_LearnSkill) message;
		this.setSkillGroupId(protocal.getSkillGroupId());
		this.setConsumePotency(protocal.getConsumePotency());
	}

	@Override
	public CS_LearnSkill javaBeanToProtocol() {
		Builder newBuilder = CS_LearnSkill.newBuilder();
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		newBuilder.setConsumePotency(this.getConsumePotency());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_LearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_LearnSkill.parseFrom(bytes);
	}
}