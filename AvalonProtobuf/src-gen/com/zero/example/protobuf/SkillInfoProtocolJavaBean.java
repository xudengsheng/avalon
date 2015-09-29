package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.SkillInfoProtocol;
import com.zero.example.protobuf.StructurePro.SkillInfoProtocol.Builder;


public class SkillInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer skillGroupId;
	private java.lang.Integer status;
	private java.lang.Integer proficiency;
	private java.lang.Integer skillGroupMaxLevel;
	private java.lang.Integer skillGroupCurrentLevel;
	
	public java.lang.Integer getSkillGroupId()
	{
		return 	skillGroupId;
	}

	public void setSkillGroupId(java.lang.Integer skillGroupId) {
		this.skillGroupId = skillGroupId;
	}	
	public java.lang.Integer getStatus()
	{
		return 	status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}	
	public java.lang.Integer getProficiency()
	{
		return 	proficiency;
	}

	public void setProficiency(java.lang.Integer proficiency) {
		this.proficiency = proficiency;
	}	
	public java.lang.Integer getSkillGroupMaxLevel()
	{
		return 	skillGroupMaxLevel;
	}

	public void setSkillGroupMaxLevel(java.lang.Integer skillGroupMaxLevel) {
		this.skillGroupMaxLevel = skillGroupMaxLevel;
	}	
	public java.lang.Integer getSkillGroupCurrentLevel()
	{
		return 	skillGroupCurrentLevel;
	}

	public void setSkillGroupCurrentLevel(java.lang.Integer skillGroupCurrentLevel) {
		this.skillGroupCurrentLevel = skillGroupCurrentLevel;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SkillInfoProtocol protocal = (SkillInfoProtocol) message;
		this.setSkillGroupId(protocal.getSkillGroupId());
		this.setStatus(protocal.getStatus());
		this.setProficiency(protocal.getProficiency());
		this.setSkillGroupMaxLevel(protocal.getSkillGroupMaxLevel());
		this.setSkillGroupCurrentLevel(protocal.getSkillGroupCurrentLevel());
	}

	@Override
	public SkillInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = SkillInfoProtocol.newBuilder();
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		newBuilder.setStatus(this.getStatus());
		newBuilder.setProficiency(this.getProficiency());
		newBuilder.setSkillGroupMaxLevel(this.getSkillGroupMaxLevel());
		newBuilder.setSkillGroupCurrentLevel(this.getSkillGroupCurrentLevel());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SkillInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SkillInfoProtocol.parseFrom(bytes);
	}
}