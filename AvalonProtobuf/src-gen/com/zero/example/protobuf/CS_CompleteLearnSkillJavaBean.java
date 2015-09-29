package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_CompleteLearnSkill;
import com.zero.example.protobuf.SectPro.CS_CompleteLearnSkill.Builder;


public class CS_CompleteLearnSkillJavaBean implements JavaProtocolTransform {

	private java.lang.Integer skillGroupId;
	
	public java.lang.Integer getSkillGroupId()
	{
		return 	skillGroupId;
	}

	public void setSkillGroupId(java.lang.Integer skillGroupId) {
		this.skillGroupId = skillGroupId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_CompleteLearnSkill protocal = (CS_CompleteLearnSkill) message;
		this.setSkillGroupId(protocal.getSkillGroupId());
	}

	@Override
	public CS_CompleteLearnSkill javaBeanToProtocol() {
		Builder newBuilder = CS_CompleteLearnSkill.newBuilder();
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_CompleteLearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_CompleteLearnSkill.parseFrom(bytes);
	}
}