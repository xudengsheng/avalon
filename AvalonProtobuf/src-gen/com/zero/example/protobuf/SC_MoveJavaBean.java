package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.SC_Move;
import com.zero.example.protobuf.ScenePro.SC_Move.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_MoveJavaBean implements JavaProtocolTransform {

	private List<SceneNodeInfoProtocolJavaBean> sceneNodeInfo;
	private java.lang.Integer toNode;
	
	public List<SceneNodeInfoProtocolJavaBean> getSceneNodeInfo()
	{
		return 	sceneNodeInfo;
	}

	public void setSceneNodeInfo(List<SceneNodeInfoProtocolJavaBean> sceneNodeInfo) {
		this.sceneNodeInfo = sceneNodeInfo;
	}	
	public java.lang.Integer getToNode()
	{
		return 	toNode;
	}

	public void setToNode(java.lang.Integer toNode) {
		this.toNode = toNode;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_Move protocal = (SC_Move) message;
		{	
		List<SceneNodeInfoProtocolJavaBean> list = new ArrayList<SceneNodeInfoProtocolJavaBean>();
		for (SceneNodeInfoProtocol JavaBean : protocal.getSceneNodeInfoList()) {
			SceneNodeInfoProtocolJavaBean protocol = new SceneNodeInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setSceneNodeInfo(list);
		}
		this.setToNode(protocal.getToNode());
	}

	@Override
	public SC_Move javaBeanToProtocol() {
		Builder newBuilder = SC_Move.newBuilder();
		{
			List<SceneNodeInfoProtocol> list = new ArrayList<SceneNodeInfoProtocol>();
			for (SceneNodeInfoProtocolJavaBean JavaBean : this.getSceneNodeInfo()) {
			list.add((SceneNodeInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllSceneNodeInfo(list);
		}
		newBuilder.setToNode(this.getToNode());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_Move bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_Move.parseFrom(bytes);
	}
}