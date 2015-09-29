package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.SC_OneAddBattleState;
import com.zero.example.protobuf.BattlePro.SC_OneAddBattleState.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_OneAddBattleStateJavaBean implements JavaProtocolTransform {

	private BattleFighterInfoProtocolJavaBean battleFighterInfo;
	private java.lang.Integer deathNoed;
	
	
	public BattleFighterInfoProtocolJavaBean getBattleFighterInfo()
	{
		return 	battleFighterInfo;
	}

	public void setBattleFighterInfo(BattleFighterInfoProtocolJavaBean battleFighterInfo) {
		this.battleFighterInfo = battleFighterInfo;
	}	
	
	public java.lang.Integer getDeathNoed()
	{
		return 	deathNoed;
	}

	public void setDeathNoed(java.lang.Integer deathNoed) {
		this.deathNoed = deathNoed;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_OneAddBattleState protocal = (SC_OneAddBattleState) message;
		{
			BattleFighterInfoProtocolJavaBean protocol = new BattleFighterInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getBattleFighterInfo());
			this.setBattleFighterInfo(protocol);
		}
		this.setDeathNoed(protocal.getDeathNoed());
	}

	@Override
	public SC_OneAddBattleState javaBeanToProtocol() {
		Builder newBuilder = SC_OneAddBattleState.newBuilder();
		{
			BattleFighterInfoProtocol protocol = this.getBattleFighterInfo().javaBeanToProtocol();
			newBuilder.setBattleFighterInfo(protocol);
		}
		newBuilder.setDeathNoed(this.getDeathNoed());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_OneAddBattleState bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_OneAddBattleState.parseFrom(bytes);
	}
}