package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.SC_BattleRoundReport;
import com.zero.example.protobuf.BattlePro.SC_BattleRoundReport.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;


public class SC_BattleRoundReportJavaBean implements JavaProtocolTransform {

	private BattleAttackFireInfoProtocolJavaBean battleAttackFireInfo;
	private List<BattleAttackBackInfoProtocolJavaBean> battleAttackBackInfo;
	private List<BattleAttackCalculateInfoProtocolJavaBean> battleAttackCalculateInfo;
	private List<BattleAttackStateInfoProtocolJavaBean> battleAttackStateInfo;
	
	
	public BattleAttackFireInfoProtocolJavaBean getBattleAttackFireInfo()
	{
		return 	battleAttackFireInfo;
	}

	public void setBattleAttackFireInfo(BattleAttackFireInfoProtocolJavaBean battleAttackFireInfo) {
		this.battleAttackFireInfo = battleAttackFireInfo;
	}	
	
	public List<BattleAttackBackInfoProtocolJavaBean> getBattleAttackBackInfo()
	{
		return 	battleAttackBackInfo;
	}

	public void setBattleAttackBackInfo(List<BattleAttackBackInfoProtocolJavaBean> battleAttackBackInfo) {
		this.battleAttackBackInfo = battleAttackBackInfo;
	}	
	public List<BattleAttackCalculateInfoProtocolJavaBean> getBattleAttackCalculateInfo()
	{
		return 	battleAttackCalculateInfo;
	}

	public void setBattleAttackCalculateInfo(List<BattleAttackCalculateInfoProtocolJavaBean> battleAttackCalculateInfo) {
		this.battleAttackCalculateInfo = battleAttackCalculateInfo;
	}	
	public List<BattleAttackStateInfoProtocolJavaBean> getBattleAttackStateInfo()
	{
		return 	battleAttackStateInfo;
	}

	public void setBattleAttackStateInfo(List<BattleAttackStateInfoProtocolJavaBean> battleAttackStateInfo) {
		this.battleAttackStateInfo = battleAttackStateInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_BattleRoundReport protocal = (SC_BattleRoundReport) message;
		{
			BattleAttackFireInfoProtocolJavaBean protocol = new BattleAttackFireInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getBattleAttackFireInfo());
			this.setBattleAttackFireInfo(protocol);
		}
		{	
		List<BattleAttackBackInfoProtocolJavaBean> list = new ArrayList<BattleAttackBackInfoProtocolJavaBean>();
		for (BattleAttackBackInfoProtocol JavaBean : protocal.getBattleAttackBackInfoList()) {
			BattleAttackBackInfoProtocolJavaBean protocol = new BattleAttackBackInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setBattleAttackBackInfo(list);
		}
		{	
		List<BattleAttackCalculateInfoProtocolJavaBean> list = new ArrayList<BattleAttackCalculateInfoProtocolJavaBean>();
		for (BattleAttackCalculateInfoProtocol JavaBean : protocal.getBattleAttackCalculateInfoList()) {
			BattleAttackCalculateInfoProtocolJavaBean protocol = new BattleAttackCalculateInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setBattleAttackCalculateInfo(list);
		}
		{	
		List<BattleAttackStateInfoProtocolJavaBean> list = new ArrayList<BattleAttackStateInfoProtocolJavaBean>();
		for (BattleAttackStateInfoProtocol JavaBean : protocal.getBattleAttackStateInfoList()) {
			BattleAttackStateInfoProtocolJavaBean protocol = new BattleAttackStateInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setBattleAttackStateInfo(list);
		}
	}

	@Override
	public SC_BattleRoundReport javaBeanToProtocol() {
		Builder newBuilder = SC_BattleRoundReport.newBuilder();
		{
			BattleAttackFireInfoProtocol protocol = this.getBattleAttackFireInfo().javaBeanToProtocol();
			newBuilder.setBattleAttackFireInfo(protocol);
		}
		{
			List<BattleAttackBackInfoProtocol> list = new ArrayList<BattleAttackBackInfoProtocol>();
			for (BattleAttackBackInfoProtocolJavaBean JavaBean : this.getBattleAttackBackInfo()) {
			list.add((BattleAttackBackInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllBattleAttackBackInfo(list);
		}
		{
			List<BattleAttackCalculateInfoProtocol> list = new ArrayList<BattleAttackCalculateInfoProtocol>();
			for (BattleAttackCalculateInfoProtocolJavaBean JavaBean : this.getBattleAttackCalculateInfo()) {
			list.add((BattleAttackCalculateInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllBattleAttackCalculateInfo(list);
		}
		{
			List<BattleAttackStateInfoProtocol> list = new ArrayList<BattleAttackStateInfoProtocol>();
			for (BattleAttackStateInfoProtocolJavaBean JavaBean : this.getBattleAttackStateInfo()) {
			list.add((BattleAttackStateInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllBattleAttackStateInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_BattleRoundReport bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_BattleRoundReport.parseFrom(bytes);
	}
}