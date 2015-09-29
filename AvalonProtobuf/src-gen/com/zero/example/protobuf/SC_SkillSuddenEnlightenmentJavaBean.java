package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_SkillSuddenEnlightenment;
import com.zero.example.protobuf.SectPro.SC_SkillSuddenEnlightenment.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_SkillSuddenEnlightenmentJavaBean implements JavaProtocolTransform {

	private SkillInfoProtocolJavaBean skillInfo;
	
	
	public SkillInfoProtocolJavaBean getSkillInfo()
	{
		return 	skillInfo;
	}

	public void setSkillInfo(SkillInfoProtocolJavaBean skillInfo) {
		this.skillInfo = skillInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_SkillSuddenEnlightenment protocal = (SC_SkillSuddenEnlightenment) message;
		{
			SkillInfoProtocolJavaBean protocol = new SkillInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getSkillInfo());
			this.setSkillInfo(protocol);
		}
	}

	@Override
	public SC_SkillSuddenEnlightenment javaBeanToProtocol() {
		Builder newBuilder = SC_SkillSuddenEnlightenment.newBuilder();
		{
			SkillInfoProtocol protocol = this.getSkillInfo().javaBeanToProtocol();
			newBuilder.setSkillInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_SkillSuddenEnlightenment bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_SkillSuddenEnlightenment.parseFrom(bytes);
	}
}