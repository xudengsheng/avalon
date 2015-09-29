package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.LearnSkillConsumeInfoProtocol;
import com.zero.example.protobuf.StructurePro.LearnSkillConsumeInfoProtocol.Builder;


public class LearnSkillConsumeInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer playerMoney;
	private java.lang.Integer playerPotency;
	
	public java.lang.Integer getPlayerMoney()
	{
		return 	playerMoney;
	}

	public void setPlayerMoney(java.lang.Integer playerMoney) {
		this.playerMoney = playerMoney;
	}	
	public java.lang.Integer getPlayerPotency()
	{
		return 	playerPotency;
	}

	public void setPlayerPotency(java.lang.Integer playerPotency) {
		this.playerPotency = playerPotency;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		LearnSkillConsumeInfoProtocol protocal = (LearnSkillConsumeInfoProtocol) message;
		this.setPlayerMoney(protocal.getPlayerMoney());
		this.setPlayerPotency(protocal.getPlayerPotency());
	}

	@Override
	public LearnSkillConsumeInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = LearnSkillConsumeInfoProtocol.newBuilder();
		newBuilder.setPlayerMoney(this.getPlayerMoney());
		newBuilder.setPlayerPotency(this.getPlayerPotency());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public LearnSkillConsumeInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return LearnSkillConsumeInfoProtocol.parseFrom(bytes);
	}
}