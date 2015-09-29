package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.SkillGroupInfoProtocol;
import com.zero.example.protobuf.StructurePro.SkillGroupInfoProtocol.Builder;


public class SkillGroupInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer skillGroupId;
	private java.lang.Integer level;
	
	public java.lang.Integer getSkillGroupId()
	{
		return 	skillGroupId;
	}

	public void setSkillGroupId(java.lang.Integer skillGroupId) {
		this.skillGroupId = skillGroupId;
	}	
	public java.lang.Integer getLevel()
	{
		return 	level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SkillGroupInfoProtocol protocal = (SkillGroupInfoProtocol) message;
		this.setSkillGroupId(protocal.getSkillGroupId());
		this.setLevel(protocal.getLevel());
	}

	@Override
	public SkillGroupInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = SkillGroupInfoProtocol.newBuilder();
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		newBuilder.setLevel(this.getLevel());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SkillGroupInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SkillGroupInfoProtocol.parseFrom(bytes);
	}
}