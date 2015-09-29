package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.SC_BattleOver;
import com.zero.example.protobuf.BattlePro.SC_BattleOver.Builder;


public class SC_BattleOverJavaBean implements JavaProtocolTransform {

	private java.lang.Long winnerId;
	
	public java.lang.Long getWinnerId()
	{
		return 	winnerId;
	}

	public void setWinnerId(java.lang.Long winnerId) {
		this.winnerId = winnerId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_BattleOver protocal = (SC_BattleOver) message;
		this.setWinnerId(protocal.getWinnerId());
	}

	@Override
	public SC_BattleOver javaBeanToProtocol() {
		Builder newBuilder = SC_BattleOver.newBuilder();
		newBuilder.setWinnerId(this.getWinnerId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_BattleOver bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_BattleOver.parseFrom(bytes);
	}
}