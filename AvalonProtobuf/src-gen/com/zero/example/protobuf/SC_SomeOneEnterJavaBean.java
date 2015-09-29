package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.SC_SomeOneEnter;
import com.zero.example.protobuf.ScenePro.SC_SomeOneEnter.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_SomeOneEnterJavaBean implements JavaProtocolTransform {

	private SceneNodeInfoProtocolJavaBean sceneNodeInfo;
	
	
	public SceneNodeInfoProtocolJavaBean getSceneNodeInfo()
	{
		return 	sceneNodeInfo;
	}

	public void setSceneNodeInfo(SceneNodeInfoProtocolJavaBean sceneNodeInfo) {
		this.sceneNodeInfo = sceneNodeInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_SomeOneEnter protocal = (SC_SomeOneEnter) message;
		{
			SceneNodeInfoProtocolJavaBean protocol = new SceneNodeInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getSceneNodeInfo());
			this.setSceneNodeInfo(protocol);
		}
	}

	@Override
	public SC_SomeOneEnter javaBeanToProtocol() {
		Builder newBuilder = SC_SomeOneEnter.newBuilder();
		{
			SceneNodeInfoProtocol protocol = this.getSceneNodeInfo().javaBeanToProtocol();
			newBuilder.setSceneNodeInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_SomeOneEnter bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_SomeOneEnter.parseFrom(bytes);
	}
}