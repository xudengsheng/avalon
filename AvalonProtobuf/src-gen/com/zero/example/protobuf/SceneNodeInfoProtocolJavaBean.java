package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.SceneNodeInfoProtocol;
import com.zero.example.protobuf.StructurePro.SceneNodeInfoProtocol.Builder;


public class SceneNodeInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Long uid;
	private java.lang.Long templateId;
	private java.lang.String name;
	private java.lang.Integer ustate;
	
	public java.lang.Long getUid()
	{
		return 	uid;
	}

	public void setUid(java.lang.Long uid) {
		this.uid = uid;
	}	
	public java.lang.Long getTemplateId()
	{
		return 	templateId;
	}

	public void setTemplateId(java.lang.Long templateId) {
		this.templateId = templateId;
	}	
	public java.lang.String getName()
	{
		return 	name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}	
	public java.lang.Integer getUstate()
	{
		return 	ustate;
	}

	public void setUstate(java.lang.Integer ustate) {
		this.ustate = ustate;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SceneNodeInfoProtocol protocal = (SceneNodeInfoProtocol) message;
		this.setUid(protocal.getUid());
		this.setTemplateId(protocal.getTemplateId());
		this.setName(protocal.getName());
		this.setUstate(protocal.getUstate());
	}

	@Override
	public SceneNodeInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = SceneNodeInfoProtocol.newBuilder();
		newBuilder.setUid(this.getUid());
		newBuilder.setTemplateId(this.getTemplateId());
		newBuilder.setName(this.getName());
		newBuilder.setUstate(this.getUstate());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SceneNodeInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SceneNodeInfoProtocol.parseFrom(bytes);
	}
}