package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.SC_BattleState;
import com.zero.example.protobuf.BattlePro.SC_BattleState.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_BattleStateJavaBean implements JavaProtocolTransform {

	private java.lang.Integer battleState;
	private java.lang.Integer prompt;
	private List<BattleFighterInfoProtocolJavaBean> battleFighterInfo;
	
	public java.lang.Integer getBattleState()
	{
		return 	battleState;
	}

	public void setBattleState(java.lang.Integer battleState) {
		this.battleState = battleState;
	}	
	public java.lang.Integer getPrompt()
	{
		return 	prompt;
	}

	public void setPrompt(java.lang.Integer prompt) {
		this.prompt = prompt;
	}	
	public List<BattleFighterInfoProtocolJavaBean> getBattleFighterInfo()
	{
		return 	battleFighterInfo;
	}

	public void setBattleFighterInfo(List<BattleFighterInfoProtocolJavaBean> battleFighterInfo) {
		this.battleFighterInfo = battleFighterInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_BattleState protocal = (SC_BattleState) message;
		this.setBattleState(protocal.getBattleState());
		this.setPrompt(protocal.getPrompt());
		{	
		List<BattleFighterInfoProtocolJavaBean> list = new ArrayList<BattleFighterInfoProtocolJavaBean>();
		for (BattleFighterInfoProtocol JavaBean : protocal.getBattleFighterInfoList()) {
			BattleFighterInfoProtocolJavaBean protocol = new BattleFighterInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setBattleFighterInfo(list);
		}
	}

	@Override
	public SC_BattleState javaBeanToProtocol() {
		Builder newBuilder = SC_BattleState.newBuilder();
		newBuilder.setBattleState(this.getBattleState());
		newBuilder.setPrompt(this.getPrompt());
		{
			List<BattleFighterInfoProtocol> list = new ArrayList<BattleFighterInfoProtocol>();
			for (BattleFighterInfoProtocolJavaBean JavaBean : this.getBattleFighterInfo()) {
			list.add((BattleFighterInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllBattleFighterInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_BattleState bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_BattleState.parseFrom(bytes);
	}
}