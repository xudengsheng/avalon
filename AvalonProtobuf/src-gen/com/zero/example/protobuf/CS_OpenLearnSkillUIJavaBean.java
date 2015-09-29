package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_OpenLearnSkillUI;
import com.zero.example.protobuf.SectPro.CS_OpenLearnSkillUI.Builder;


public class CS_OpenLearnSkillUIJavaBean implements JavaProtocolTransform {

	private java.lang.Integer npcId;
	
	public java.lang.Integer getNpcId()
	{
		return 	npcId;
	}

	public void setNpcId(java.lang.Integer npcId) {
		this.npcId = npcId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_OpenLearnSkillUI protocal = (CS_OpenLearnSkillUI) message;
		this.setNpcId(protocal.getNpcId());
	}

	@Override
	public CS_OpenLearnSkillUI javaBeanToProtocol() {
		Builder newBuilder = CS_OpenLearnSkillUI.newBuilder();
		newBuilder.setNpcId(this.getNpcId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_OpenLearnSkillUI bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_OpenLearnSkillUI.parseFrom(bytes);
	}
}