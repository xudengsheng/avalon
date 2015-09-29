package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.CS_Move;
import com.zero.example.protobuf.ScenePro.CS_Move.Builder;


public class CS_MoveJavaBean implements JavaProtocolTransform {

	private java.lang.Integer toNode;
	
	public java.lang.Integer getToNode()
	{
		return 	toNode;
	}

	public void setToNode(java.lang.Integer toNode) {
		this.toNode = toNode;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_Move protocal = (CS_Move) message;
		this.setToNode(protocal.getToNode());
	}

	@Override
	public CS_Move javaBeanToProtocol() {
		Builder newBuilder = CS_Move.newBuilder();
		newBuilder.setToNode(this.getToNode());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_Move bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_Move.parseFrom(bytes);
	}
}