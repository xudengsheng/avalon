package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.StayOnlineInfoProtocol;
import com.zero.example.protobuf.StructurePro.StayOnlineInfoProtocol.Builder;


public class StayOnlineInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Boolean learnSkill;
	private java.lang.Boolean workPartTime;
	private java.lang.Integer learnSkillTimeLeftSec;
	private java.lang.Integer workTimeLeftSec;
	
	public java.lang.Boolean getLearnSkill()
	{
		return 	learnSkill;
	}

	public void setLearnSkill(java.lang.Boolean learnSkill) {
		this.learnSkill = learnSkill;
	}	
	public java.lang.Boolean getWorkPartTime()
	{
		return 	workPartTime;
	}

	public void setWorkPartTime(java.lang.Boolean workPartTime) {
		this.workPartTime = workPartTime;
	}	
	public java.lang.Integer getLearnSkillTimeLeftSec()
	{
		return 	learnSkillTimeLeftSec;
	}

	public void setLearnSkillTimeLeftSec(java.lang.Integer learnSkillTimeLeftSec) {
		this.learnSkillTimeLeftSec = learnSkillTimeLeftSec;
	}	
	public java.lang.Integer getWorkTimeLeftSec()
	{
		return 	workTimeLeftSec;
	}

	public void setWorkTimeLeftSec(java.lang.Integer workTimeLeftSec) {
		this.workTimeLeftSec = workTimeLeftSec;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		StayOnlineInfoProtocol protocal = (StayOnlineInfoProtocol) message;
		this.setLearnSkill(protocal.getLearnSkill());
		this.setWorkPartTime(protocal.getWorkPartTime());
		this.setLearnSkillTimeLeftSec(protocal.getLearnSkillTimeLeftSec());
		this.setWorkTimeLeftSec(protocal.getWorkTimeLeftSec());
	}

	@Override
	public StayOnlineInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = StayOnlineInfoProtocol.newBuilder();
		newBuilder.setLearnSkill(this.getLearnSkill());
		newBuilder.setWorkPartTime(this.getWorkPartTime());
		newBuilder.setLearnSkillTimeLeftSec(this.getLearnSkillTimeLeftSec());
		newBuilder.setWorkTimeLeftSec(this.getWorkTimeLeftSec());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public StayOnlineInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return StayOnlineInfoProtocol.parseFrom(bytes);
	}
}