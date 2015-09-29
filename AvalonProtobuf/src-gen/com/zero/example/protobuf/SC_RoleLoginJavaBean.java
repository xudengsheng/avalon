package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.LoginPro.*;
import com.zero.example.protobuf.LoginPro.SC_RoleLogin;
import com.zero.example.protobuf.LoginPro.SC_RoleLogin.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.*;


public class SC_RoleLoginJavaBean implements JavaProtocolTransform {

	private PlayerInfoProtocolJavaBean playerInfo;
	private List<MissionInfoProtocolJavaBean> missionInfo;
	private List<java.lang.Integer> chapterOpen;
	private List<java.lang.Integer> gotRewardMissions;
	private List<MissionInfoProtocolJavaBean> autoReceiveMissions;
	private StayOnlineInfoProtocolJavaBean stayOnlineInfo;
	private java.lang.Long currentTimestamp;
	
	
	public PlayerInfoProtocolJavaBean getPlayerInfo()
	{
		return 	playerInfo;
	}

	public void setPlayerInfo(PlayerInfoProtocolJavaBean playerInfo) {
		this.playerInfo = playerInfo;
	}	
	
	public List<MissionInfoProtocolJavaBean> getMissionInfo()
	{
		return 	missionInfo;
	}

	public void setMissionInfo(List<MissionInfoProtocolJavaBean> missionInfo) {
		this.missionInfo = missionInfo;
	}	
	public List<java.lang.Integer> getChapterOpen()
	{
		return 	chapterOpen;
	}

	public void setChapterOpen(List<java.lang.Integer> chapterOpen) {
		this.chapterOpen = chapterOpen;
	}	
	public List<java.lang.Integer> getGotRewardMissions()
	{
		return 	gotRewardMissions;
	}

	public void setGotRewardMissions(List<java.lang.Integer> gotRewardMissions) {
		this.gotRewardMissions = gotRewardMissions;
	}	
	public List<MissionInfoProtocolJavaBean> getAutoReceiveMissions()
	{
		return 	autoReceiveMissions;
	}

	public void setAutoReceiveMissions(List<MissionInfoProtocolJavaBean> autoReceiveMissions) {
		this.autoReceiveMissions = autoReceiveMissions;
	}	
	
	public StayOnlineInfoProtocolJavaBean getStayOnlineInfo()
	{
		return 	stayOnlineInfo;
	}

	public void setStayOnlineInfo(StayOnlineInfoProtocolJavaBean stayOnlineInfo) {
		this.stayOnlineInfo = stayOnlineInfo;
	}	
	
	public java.lang.Long getCurrentTimestamp()
	{
		return 	currentTimestamp;
	}

	public void setCurrentTimestamp(java.lang.Long currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_RoleLogin protocal = (SC_RoleLogin) message;
		{
			PlayerInfoProtocolJavaBean protocol = new PlayerInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getPlayerInfo());
			this.setPlayerInfo(protocol);
		}
		{	
		List<MissionInfoProtocolJavaBean> list = new ArrayList<MissionInfoProtocolJavaBean>();
		for (MissionInfoProtocol JavaBean : protocal.getMissionInfoList()) {
			MissionInfoProtocolJavaBean protocol = new MissionInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setMissionInfo(list);
		}
		{	
		List<java.lang.Integer> list = new ArrayList<java.lang.Integer>();
		for (java.lang.Integer JavaBean : protocal.getChapterOpenList()) {
			list.add(JavaBean);
		}
		this.setChapterOpen(list);
		}
		{	
		List<java.lang.Integer> list = new ArrayList<java.lang.Integer>();
		for (java.lang.Integer JavaBean : protocal.getGotRewardMissionsList()) {
			list.add(JavaBean);
		}
		this.setGotRewardMissions(list);
		}
		{	
		List<MissionInfoProtocolJavaBean> list = new ArrayList<MissionInfoProtocolJavaBean>();
		for (MissionInfoProtocol JavaBean : protocal.getAutoReceiveMissionsList()) {
			MissionInfoProtocolJavaBean protocol = new MissionInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setAutoReceiveMissions(list);
		}
		{
			StayOnlineInfoProtocolJavaBean protocol = new StayOnlineInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getStayOnlineInfo());
			this.setStayOnlineInfo(protocol);
		}
		this.setCurrentTimestamp(protocal.getCurrentTimestamp());
	}

	@Override
	public SC_RoleLogin javaBeanToProtocol() {
		Builder newBuilder = SC_RoleLogin.newBuilder();
		{
			PlayerInfoProtocol protocol = this.getPlayerInfo().javaBeanToProtocol();
			newBuilder.setPlayerInfo(protocol);
		}
		{
			List<MissionInfoProtocol> list = new ArrayList<MissionInfoProtocol>();
			for (MissionInfoProtocolJavaBean JavaBean : this.getMissionInfo()) {
			list.add((MissionInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllMissionInfo(list);
		}
		{
			List<java.lang.Integer> list = new ArrayList<java.lang.Integer>();
			for (java.lang.Integer JavaBean : this.getChapterOpen()) {
				list.add(JavaBean);
			}
			newBuilder.addAllChapterOpen(list);
		}
		{
			List<java.lang.Integer> list = new ArrayList<java.lang.Integer>();
			for (java.lang.Integer JavaBean : this.getGotRewardMissions()) {
				list.add(JavaBean);
			}
			newBuilder.addAllGotRewardMissions(list);
		}
		{
			List<MissionInfoProtocol> list = new ArrayList<MissionInfoProtocol>();
			for (MissionInfoProtocolJavaBean JavaBean : this.getAutoReceiveMissions()) {
			list.add((MissionInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllAutoReceiveMissions(list);
		}
		{
			StayOnlineInfoProtocol protocol = this.getStayOnlineInfo().javaBeanToProtocol();
			newBuilder.setStayOnlineInfo(protocol);
		}
		newBuilder.setCurrentTimestamp(this.getCurrentTimestamp());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_RoleLogin bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_RoleLogin.parseFrom(bytes);
	}
}