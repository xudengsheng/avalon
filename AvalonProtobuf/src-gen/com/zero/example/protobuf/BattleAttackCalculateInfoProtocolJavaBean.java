package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.BattleAttackCalculateInfoProtocol;
import com.zero.example.protobuf.StructurePro.BattleAttackCalculateInfoProtocol.Builder;


public class BattleAttackCalculateInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long defenderId;
	private java.lang.Integer replyId;
	private java.lang.Long attackerId;
	private java.lang.String damageResult;
	
	public java.lang.Long getDefenderId()
	{
		return 	defenderId;
	}

	public void setDefenderId(java.lang.Long defenderId) {
		this.defenderId = defenderId;
	}	
	public java.lang.Integer getReplyId()
	{
		return 	replyId;
	}

	public void setReplyId(java.lang.Integer replyId) {
		this.replyId = replyId;
	}	
	public java.lang.Long getAttackerId()
	{
		return 	attackerId;
	}

	public void setAttackerId(java.lang.Long attackerId) {
		this.attackerId = attackerId;
	}	
	public java.lang.String getDamageResult()
	{
		return 	damageResult;
	}

	public void setDamageResult(java.lang.String damageResult) {
		this.damageResult = damageResult;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		BattleAttackCalculateInfoProtocol protocal = (BattleAttackCalculateInfoProtocol) message;
		this.setDefenderId(protocal.getDefenderId());
		this.setReplyId(protocal.getReplyId());
		this.setAttackerId(protocal.getAttackerId());
		this.setDamageResult(protocal.getDamageResult());
	}

	@Override
	public BattleAttackCalculateInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = BattleAttackCalculateInfoProtocol.newBuilder();
		newBuilder.setDefenderId(this.getDefenderId());
		newBuilder.setReplyId(this.getReplyId());
		newBuilder.setAttackerId(this.getAttackerId());
		newBuilder.setDamageResult(this.getDamageResult());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public BattleAttackCalculateInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return BattleAttackCalculateInfoProtocol.parseFrom(bytes);
	}
}