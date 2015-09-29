package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.SC_HookSpeedUp;
import com.zero.example.protobuf.HookPro.SC_HookSpeedUp.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_HookSpeedUpJavaBean implements JavaProtocolTransform {

	private HookInfoProtocolJavaBean hookInfo;
	
	
	public HookInfoProtocolJavaBean getHookInfo()
	{
		return 	hookInfo;
	}

	public void setHookInfo(HookInfoProtocolJavaBean hookInfo) {
		this.hookInfo = hookInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_HookSpeedUp protocal = (SC_HookSpeedUp) message;
		{
			HookInfoProtocolJavaBean protocol = new HookInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getHookInfo());
			this.setHookInfo(protocol);
		}
	}

	@Override
	public SC_HookSpeedUp javaBeanToProtocol() {
		Builder newBuilder = SC_HookSpeedUp.newBuilder();
		{
			HookInfoProtocol protocol = this.getHookInfo().javaBeanToProtocol();
			newBuilder.setHookInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_HookSpeedUp bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_HookSpeedUp.parseFrom(bytes);
	}
}