package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.BattleAttackBackInfoProtocol;
import com.zero.example.protobuf.StructurePro.BattleAttackBackInfoProtocol.Builder;


public class BattleAttackBackInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long defenderId;
	private java.lang.Integer touchSkillId;
	private java.lang.Integer feedbackId;
	private java.lang.Integer resultState;
	private java.lang.Integer defenderHp;
	
	public java.lang.Long getDefenderId()
	{
		return 	defenderId;
	}

	public void setDefenderId(java.lang.Long defenderId) {
		this.defenderId = defenderId;
	}	
	public java.lang.Integer getTouchSkillId()
	{
		return 	touchSkillId;
	}

	public void setTouchSkillId(java.lang.Integer touchSkillId) {
		this.touchSkillId = touchSkillId;
	}	
	public java.lang.Integer getFeedbackId()
	{
		return 	feedbackId;
	}

	public void setFeedbackId(java.lang.Integer feedbackId) {
		this.feedbackId = feedbackId;
	}	
	public java.lang.Integer getResultState()
	{
		return 	resultState;
	}

	public void setResultState(java.lang.Integer resultState) {
		this.resultState = resultState;
	}	
	public java.lang.Integer getDefenderHp()
	{
		return 	defenderHp;
	}

	public void setDefenderHp(java.lang.Integer defenderHp) {
		this.defenderHp = defenderHp;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		BattleAttackBackInfoProtocol protocal = (BattleAttackBackInfoProtocol) message;
		this.setDefenderId(protocal.getDefenderId());
		this.setTouchSkillId(protocal.getTouchSkillId());
		this.setFeedbackId(protocal.getFeedbackId());
		this.setResultState(protocal.getResultState());
		this.setDefenderHp(protocal.getDefenderHp());
	}

	@Override
	public BattleAttackBackInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = BattleAttackBackInfoProtocol.newBuilder();
		newBuilder.setDefenderId(this.getDefenderId());
		newBuilder.setTouchSkillId(this.getTouchSkillId());
		newBuilder.setFeedbackId(this.getFeedbackId());
		newBuilder.setResultState(this.getResultState());
		newBuilder.setDefenderHp(this.getDefenderHp());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public BattleAttackBackInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return BattleAttackBackInfoProtocol.parseFrom(bytes);
	}
}