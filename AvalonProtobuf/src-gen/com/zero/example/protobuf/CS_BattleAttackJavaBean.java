package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.CS_BattleAttack;
import com.zero.example.protobuf.BattlePro.CS_BattleAttack.Builder;


public class CS_BattleAttackJavaBean implements JavaProtocolTransform {

	private java.lang.Long targetId;
	
	public java.lang.Long getTargetId()
	{
		return 	targetId;
	}

	public void setTargetId(java.lang.Long targetId) {
		this.targetId = targetId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_BattleAttack protocal = (CS_BattleAttack) message;
		this.setTargetId(protocal.getTargetId());
	}

	@Override
	public CS_BattleAttack javaBeanToProtocol() {
		Builder newBuilder = CS_BattleAttack.newBuilder();
		newBuilder.setTargetId(this.getTargetId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_BattleAttack bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_BattleAttack.parseFrom(bytes);
	}
}