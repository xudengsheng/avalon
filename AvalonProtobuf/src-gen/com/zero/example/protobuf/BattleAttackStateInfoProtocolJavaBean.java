package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.BattleAttackStateInfoProtocol;
import com.zero.example.protobuf.StructurePro.BattleAttackStateInfoProtocol.Builder;


public class BattleAttackStateInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long defenderId;
	private java.lang.Integer buffId;
	private java.lang.String damageResult;
	private java.lang.Long stateid;
	
	public java.lang.Long getDefenderId()
	{
		return 	defenderId;
	}

	public void setDefenderId(java.lang.Long defenderId) {
		this.defenderId = defenderId;
	}	
	public java.lang.Integer getBuffId()
	{
		return 	buffId;
	}

	public void setBuffId(java.lang.Integer buffId) {
		this.buffId = buffId;
	}	
	public java.lang.String getDamageResult()
	{
		return 	damageResult;
	}

	public void setDamageResult(java.lang.String damageResult) {
		this.damageResult = damageResult;
	}	
	public java.lang.Long getStateid()
	{
		return 	stateid;
	}

	public void setStateid(java.lang.Long stateid) {
		this.stateid = stateid;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		BattleAttackStateInfoProtocol protocal = (BattleAttackStateInfoProtocol) message;
		this.setDefenderId(protocal.getDefenderId());
		this.setBuffId(protocal.getBuffId());
		this.setDamageResult(protocal.getDamageResult());
		this.setStateid(protocal.getStateid());
	}

	@Override
	public BattleAttackStateInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = BattleAttackStateInfoProtocol.newBuilder();
		newBuilder.setDefenderId(this.getDefenderId());
		newBuilder.setBuffId(this.getBuffId());
		newBuilder.setDamageResult(this.getDamageResult());
		newBuilder.setStateid(this.getStateid());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public BattleAttackStateInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return BattleAttackStateInfoProtocol.parseFrom(bytes);
	}
}