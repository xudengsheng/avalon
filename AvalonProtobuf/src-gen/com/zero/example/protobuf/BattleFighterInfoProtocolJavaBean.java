package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.BattleFighterInfoProtocol;
import com.zero.example.protobuf.StructurePro.BattleFighterInfoProtocol.Builder;


public class BattleFighterInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long fighterId;
	private java.lang.String name;
	private java.lang.Integer hp;
	private java.lang.Integer maxHp;
	private java.lang.Integer postion;
	
	public java.lang.Long getFighterId()
	{
		return 	fighterId;
	}

	public void setFighterId(java.lang.Long fighterId) {
		this.fighterId = fighterId;
	}	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	public java.lang.Integer getHp()
	{
		return 	hp;
	}

	public void setHp(java.lang.Integer hp) {
		this.hp = hp;
	}	
	public java.lang.Integer getMaxHp()
	{
		return 	maxHp;
	}

	public void setMaxHp(java.lang.Integer maxHp) {
		this.maxHp = maxHp;
	}	
	public java.lang.Integer getPostion()
	{
		return 	postion;
	}

	public void setPostion(java.lang.Integer postion) {
		this.postion = postion;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		BattleFighterInfoProtocol protocal = (BattleFighterInfoProtocol) message;
		this.setFighterId(protocal.getFighterId());
		this.setName(protocal.getName());
		this.setHp(protocal.getHp());
		this.setMaxHp(protocal.getMaxHp());
		this.setPostion(protocal.getPostion());
	}

	@Override
	public BattleFighterInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = BattleFighterInfoProtocol.newBuilder();
		newBuilder.setFighterId(this.getFighterId());
		newBuilder.setName(this.getName());
		newBuilder.setHp(this.getHp());
		newBuilder.setMaxHp(this.getMaxHp());
		newBuilder.setPostion(this.getPostion());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public BattleFighterInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return BattleFighterInfoProtocol.parseFrom(bytes);
	}
}