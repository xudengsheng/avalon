package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_QuitLearnSkill;
import com.zero.example.protobuf.SectPro.CS_QuitLearnSkill.Builder;


public class CS_QuitLearnSkillJavaBean implements JavaProtocolTransform {

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
		CS_QuitLearnSkill protocal = (CS_QuitLearnSkill) message;
		this.setSkillGroupId(protocal.getSkillGroupId());
	}

	@Override
	public CS_QuitLearnSkill javaBeanToProtocol() {
		Builder newBuilder = CS_QuitLearnSkill.newBuilder();
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_QuitLearnSkill bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_QuitLearnSkill.parseFrom(bytes);
	}
}