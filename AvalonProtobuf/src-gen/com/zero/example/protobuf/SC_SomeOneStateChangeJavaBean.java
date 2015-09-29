package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.SC_SomeOneStateChange;
import com.zero.example.protobuf.ScenePro.SC_SomeOneStateChange.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_SomeOneStateChangeJavaBean implements JavaProtocolTransform {

	private List<SceneNodeInfoProtocolJavaBean> sceneNodeInfo;
	
	public List<SceneNodeInfoProtocolJavaBean> getSceneNodeInfo()
	{
		return 	sceneNodeInfo;
	}

	public void setSceneNodeInfo(List<SceneNodeInfoProtocolJavaBean> sceneNodeInfo) {
		this.sceneNodeInfo = sceneNodeInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_SomeOneStateChange protocal = (SC_SomeOneStateChange) message;
		{	
		List<SceneNodeInfoProtocolJavaBean> list = new ArrayList<SceneNodeInfoProtocolJavaBean>();
		for (SceneNodeInfoProtocol JavaBean : protocal.getSceneNodeInfoList()) {
			SceneNodeInfoProtocolJavaBean protocol = new SceneNodeInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setSceneNodeInfo(list);
		}
	}

	@Override
	public SC_SomeOneStateChange javaBeanToProtocol() {
		Builder newBuilder = SC_SomeOneStateChange.newBuilder();
		{
			List<SceneNodeInfoProtocol> list = new ArrayList<SceneNodeInfoProtocol>();
			for (SceneNodeInfoProtocolJavaBean JavaBean : this.getSceneNodeInfo()) {
			list.add((SceneNodeInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllSceneNodeInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_SomeOneStateChange bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_SomeOneStateChange.parseFrom(bytes);
	}
}