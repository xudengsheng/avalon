package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.PlayerInfoProtocol;
import com.zero.example.protobuf.StructurePro.PlayerInfoProtocol.Builder;
import java.util.List;
import java.util.ArrayList;


public class PlayerInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long playerId;
	private java.lang.String name;
	private java.lang.Integer level;
	private java.lang.Long exp;
	private java.lang.Integer sex;
	private java.lang.Long money;
	private java.lang.Long rmb;
	private java.lang.Integer qianneng;
	private java.lang.Integer tizhi;
	private java.lang.Integer gengu;
	private java.lang.Integer wuxing;
	private java.lang.Integer lingqiao;
	private java.lang.Integer hp;
	private java.lang.Integer tili;
	private java.lang.Integer mp;
	private java.lang.Integer baoshi;
	private java.lang.Integer age;
	private java.lang.Integer fuyuan;
	private java.lang.Integer yirong;
	private java.lang.Integer sceneNode;
	private List<SkillGroupInfoProtocolJavaBean> skillGroupInfo;
	private java.lang.Integer masterId;
	private java.lang.Integer masterType;
	private java.lang.Integer learnSkillTime;
	private java.lang.Integer skillGroupId;
	private java.lang.Integer skillGroupLevel;
	private java.lang.Integer currentTimestamp;
	
	public java.lang.Long getPlayerId()
	{
		return 	playerId;
	}

	public void setPlayerId(java.lang.Long playerId) {
		this.playerId = playerId;
	}	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	public java.lang.Integer getLevel()
	{
		return 	level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}	
	public java.lang.Long getExp()
	{
		return 	exp;
	}

	public void setExp(java.lang.Long exp) {
		this.exp = exp;
	}	
	public java.lang.Integer getSex()
	{
		return 	sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}	
	public java.lang.Long getMoney()
	{
		return 	money;
	}

	public void setMoney(java.lang.Long money) {
		this.money = money;
	}	
	public java.lang.Long getRmb()
	{
		return 	rmb;
	}

	public void setRmb(java.lang.Long rmb) {
		this.rmb = rmb;
	}	
	public java.lang.Integer getQianneng()
	{
		return 	qianneng;
	}

	public void setQianneng(java.lang.Integer qianneng) {
		this.qianneng = qianneng;
	}	
	public java.lang.Integer getTizhi()
	{
		return 	tizhi;
	}

	public void setTizhi(java.lang.Integer tizhi) {
		this.tizhi = tizhi;
	}	
	public java.lang.Integer getGengu()
	{
		return 	gengu;
	}

	public void setGengu(java.lang.Integer gengu) {
		this.gengu = gengu;
	}	
	public java.lang.Integer getWuxing()
	{
		return 	wuxing;
	}

	public void setWuxing(java.lang.Integer wuxing) {
		this.wuxing = wuxing;
	}	
	public java.lang.Integer getLingqiao()
	{
		return 	lingqiao;
	}

	public void setLingqiao(java.lang.Integer lingqiao) {
		this.lingqiao = lingqiao;
	}	
	public java.lang.Integer getHp()
	{
		return 	hp;
	}

	public void setHp(java.lang.Integer hp) {
		this.hp = hp;
	}	
	public java.lang.Integer getTili()
	{
		return 	tili;
	}

	public void setTili(java.lang.Integer tili) {
		this.tili = tili;
	}	
	public java.lang.Integer getMp()
	{
		return 	mp;
	}

	public void setMp(java.lang.Integer mp) {
		this.mp = mp;
	}	
	public java.lang.Integer getBaoshi()
	{
		return 	baoshi;
	}

	public void setBaoshi(java.lang.Integer baoshi) {
		this.baoshi = baoshi;
	}	
	public java.lang.Integer getAge()
	{
		return 	age;
	}

	public void setAge(java.lang.Integer age) {
		this.age = age;
	}	
	public java.lang.Integer getFuyuan()
	{
		return 	fuyuan;
	}

	public void setFuyuan(java.lang.Integer fuyuan) {
		this.fuyuan = fuyuan;
	}	
	public java.lang.Integer getYirong()
	{
		return 	yirong;
	}

	public void setYirong(java.lang.Integer yirong) {
		this.yirong = yirong;
	}	
	public java.lang.Integer getSceneNode()
	{
		return 	sceneNode;
	}

	public void setSceneNode(java.lang.Integer sceneNode) {
		this.sceneNode = sceneNode;
	}	
	public List<SkillGroupInfoProtocolJavaBean> getSkillGroupInfo()
	{
		return 	skillGroupInfo;
	}

	public void setSkillGroupInfo(List<SkillGroupInfoProtocolJavaBean> skillGroupInfo) {
		this.skillGroupInfo = skillGroupInfo;
	}	
	public java.lang.Integer getMasterId()
	{
		return 	masterId;
	}

	public void setMasterId(java.lang.Integer masterId) {
		this.masterId = masterId;
	}	
	public java.lang.Integer getMasterType()
	{
		return 	masterType;
	}

	public void setMasterType(java.lang.Integer masterType) {
		this.masterType = masterType;
	}	
	public java.lang.Integer getLearnSkillTime()
	{
		return 	learnSkillTime;
	}

	public void setLearnSkillTime(java.lang.Integer learnSkillTime) {
		this.learnSkillTime = learnSkillTime;
	}	
	public java.lang.Integer getSkillGroupId()
	{
		return 	skillGroupId;
	}

	public void setSkillGroupId(java.lang.Integer skillGroupId) {
		this.skillGroupId = skillGroupId;
	}	
	public java.lang.Integer getSkillGroupLevel()
	{
		return 	skillGroupLevel;
	}

	public void setSkillGroupLevel(java.lang.Integer skillGroupLevel) {
		this.skillGroupLevel = skillGroupLevel;
	}	
	public java.lang.Integer getCurrentTimestamp()
	{
		return 	currentTimestamp;
	}

	public void setCurrentTimestamp(java.lang.Integer currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		PlayerInfoProtocol protocal = (PlayerInfoProtocol) message;
		this.setPlayerId(protocal.getPlayerId());
		this.setName(protocal.getName());
		this.setLevel(protocal.getLevel());
		this.setExp(protocal.getExp());
		this.setSex(protocal.getSex());
		this.setMoney(protocal.getMoney());
		this.setRmb(protocal.getRmb());
		this.setQianneng(protocal.getQianneng());
		this.setTizhi(protocal.getTizhi());
		this.setGengu(protocal.getGengu());
		this.setWuxing(protocal.getWuxing());
		this.setLingqiao(protocal.getLingqiao());
		this.setHp(protocal.getHp());
		this.setTili(protocal.getTili());
		this.setMp(protocal.getMp());
		this.setBaoshi(protocal.getBaoshi());
		this.setAge(protocal.getAge());
		this.setFuyuan(protocal.getFuyuan());
		this.setYirong(protocal.getYirong());
		this.setSceneNode(protocal.getSceneNode());
		{	
		List<SkillGroupInfoProtocolJavaBean> list = new ArrayList<SkillGroupInfoProtocolJavaBean>();
		for (SkillGroupInfoProtocol JavaBean : protocal.getSkillGroupInfoList()) {
			SkillGroupInfoProtocolJavaBean protocol = new SkillGroupInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setSkillGroupInfo(list);
		}
		this.setMasterId(protocal.getMasterId());
		this.setMasterType(protocal.getMasterType());
		this.setLearnSkillTime(protocal.getLearnSkillTime());
		this.setSkillGroupId(protocal.getSkillGroupId());
		this.setSkillGroupLevel(protocal.getSkillGroupLevel());
		this.setCurrentTimestamp(protocal.getCurrentTimestamp());
	}

	@Override
	public PlayerInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = PlayerInfoProtocol.newBuilder();
		newBuilder.setPlayerId(this.getPlayerId());
		newBuilder.setName(this.getName());
		newBuilder.setLevel(this.getLevel());
		newBuilder.setExp(this.getExp());
		newBuilder.setSex(this.getSex());
		newBuilder.setMoney(this.getMoney());
		newBuilder.setRmb(this.getRmb());
		newBuilder.setQianneng(this.getQianneng());
		newBuilder.setTizhi(this.getTizhi());
		newBuilder.setGengu(this.getGengu());
		newBuilder.setWuxing(this.getWuxing());
		newBuilder.setLingqiao(this.getLingqiao());
		newBuilder.setHp(this.getHp());
		newBuilder.setTili(this.getTili());
		newBuilder.setMp(this.getMp());
		newBuilder.setBaoshi(this.getBaoshi());
		newBuilder.setAge(this.getAge());
		newBuilder.setFuyuan(this.getFuyuan());
		newBuilder.setYirong(this.getYirong());
		newBuilder.setSceneNode(this.getSceneNode());
		{
			List<SkillGroupInfoProtocol> list = new ArrayList<SkillGroupInfoProtocol>();
			for (SkillGroupInfoProtocolJavaBean JavaBean : this.getSkillGroupInfo()) {
			list.add((SkillGroupInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllSkillGroupInfo(list);
		}
		newBuilder.setMasterId(this.getMasterId());
		newBuilder.setMasterType(this.getMasterType());
		newBuilder.setLearnSkillTime(this.getLearnSkillTime());
		newBuilder.setSkillGroupId(this.getSkillGroupId());
		newBuilder.setSkillGroupLevel(this.getSkillGroupLevel());
		newBuilder.setCurrentTimestamp(this.getCurrentTimestamp());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public PlayerInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return PlayerInfoProtocol.parseFrom(bytes);
	}
}