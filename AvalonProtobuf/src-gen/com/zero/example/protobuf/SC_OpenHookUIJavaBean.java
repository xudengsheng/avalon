package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.SC_OpenHookUI;
import com.zero.example.protobuf.HookPro.SC_OpenHookUI.Builder;
import java.util.List;
import java.util.ArrayList;
import com.zero.example.protobuf.StructurePro.*;


public class SC_OpenHookUIJavaBean implements JavaProtocolTransform {

	private List<HookInfoProtocolJavaBean> hookInfo;
	
	public List<HookInfoProtocolJavaBean> getHookInfo()
	{
		return 	hookInfo;
	}

	public void setHookInfo(List<HookInfoProtocolJavaBean> hookInfo) {
		this.hookInfo = hookInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_OpenHookUI protocal = (SC_OpenHookUI) message;
		{	
		List<HookInfoProtocolJavaBean> list = new ArrayList<HookInfoProtocolJavaBean>();
		for (HookInfoProtocol JavaBean : protocal.getHookInfoList()) {
			HookInfoProtocolJavaBean protocol = new HookInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setHookInfo(list);
		}
	}

	@Override
	public SC_OpenHookUI javaBeanToProtocol() {
		Builder newBuilder = SC_OpenHookUI.newBuilder();
		{
			List<HookInfoProtocol> list = new ArrayList<HookInfoProtocol>();
			for (HookInfoProtocolJavaBean JavaBean : this.getHookInfo()) {
			list.add((HookInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllHookInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_OpenHookUI bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_OpenHookUI.parseFrom(bytes);
	}
}