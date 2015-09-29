package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.BattleAttackFireInfoProtocol;
import com.zero.example.protobuf.StructurePro.BattleAttackFireInfoProtocol.Builder;
import java.util.List;
import java.util.ArrayList;


public class BattleAttackFireInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long attackerId;
	private java.lang.Long weaponId;
	private java.lang.Integer skillId;
	private List<java.lang.Long> defenderId;
	private java.lang.Integer bodyPostion;
	
	public java.lang.Long getAttackerId()
	{
		return 	attackerId;
	}

	public void setAttackerId(java.lang.Long attackerId) {
		this.attackerId = attackerId;
	}	
	public java.lang.Long getWeaponId()
	{
		return 	weaponId;
	}

	public void setWeaponId(java.lang.Long weaponId) {
		this.weaponId = weaponId;
	}	
	public java.lang.Integer getSkillId()
	{
		return 	skillId;
	}

	public void setSkillId(java.lang.Integer skillId) {
		this.skillId = skillId;
	}	
	public List<java.lang.Long> getDefenderId()
	{
		return 	defenderId;
	}

	public void setDefenderId(List<java.lang.Long> defenderId) {
		this.defenderId = defenderId;
	}	
	public java.lang.Integer getBodyPostion()
	{
		return 	bodyPostion;
	}

	public void setBodyPostion(java.lang.Integer bodyPostion) {
		this.bodyPostion = bodyPostion;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		BattleAttackFireInfoProtocol protocal = (BattleAttackFireInfoProtocol) message;
		this.setAttackerId(protocal.getAttackerId());
		this.setWeaponId(protocal.getWeaponId());
		this.setSkillId(protocal.getSkillId());
		{	
		List<java.lang.Long> list = new ArrayList<java.lang.Long>();
		for (java.lang.Long JavaBean : protocal.getDefenderIdList()) {
			list.add(JavaBean);
		}
		this.setDefenderId(list);
		}
		this.setBodyPostion(protocal.getBodyPostion());
	}

	@Override
	public BattleAttackFireInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = BattleAttackFireInfoProtocol.newBuilder();
		newBuilder.setAttackerId(this.getAttackerId());
		newBuilder.setWeaponId(this.getWeaponId());
		newBuilder.setSkillId(this.getSkillId());
		{
			List<java.lang.Long> list = new ArrayList<java.lang.Long>();
			for (java.lang.Long JavaBean : this.getDefenderId()) {
				list.add(JavaBean);
			}
			newBuilder.addAllDefenderId(list);
		}
		newBuilder.setBodyPostion(this.getBodyPostion());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public BattleAttackFireInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return BattleAttackFireInfoProtocol.parseFrom(bytes);
	}
}