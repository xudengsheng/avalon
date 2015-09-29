package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.StructurePro.*;
import com.zero.example.protobuf.StructurePro.HookInfoProtocol;
import com.zero.example.protobuf.StructurePro.HookInfoProtocol.Builder;
import java.util.List;
import java.util.ArrayList;


public class HookInfoProtocolJavaBean implements JavaProtocolTransform {

	private java.lang.Integer hookId;
	private java.lang.String hookName;
	private java.lang.Integer duration;
	private List<HookRewardInfoProtocolJavaBean> rewardInfo;
	private List<HookConsumeInfoProtocolJavaBean> consumeInfo;
	
	public java.lang.Integer getHookId()
	{
		return 	hookId;
	}

	public void setHookId(java.lang.Integer hookId) {
		this.hookId = hookId;
	}	
	public java.lang.String getHookName()
	{
		return 	hookName;
	}

	public void setHookName(java.lang.String hookName) {
		this.hookName = hookName;
	}	
	public java.lang.Integer getDuration()
	{
		return 	duration;
	}

	public void setDuration(java.lang.Integer duration) {
		this.duration = duration;
	}	
	public List<HookRewardInfoProtocolJavaBean> getRewardInfo()
	{
		return 	rewardInfo;
	}

	public void setRewardInfo(List<HookRewardInfoProtocolJavaBean> rewardInfo) {
		this.rewardInfo = rewardInfo;
	}	
	public List<HookConsumeInfoProtocolJavaBean> getConsumeInfo()
	{
		return 	consumeInfo;
	}

	public void setConsumeInfo(List<HookConsumeInfoProtocolJavaBean> consumeInfo) {
		this.consumeInfo = consumeInfo;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		HookInfoProtocol protocal = (HookInfoProtocol) message;
		this.setHookId(protocal.getHookId());
		this.setHookName(protocal.getHookName());
		this.setDuration(protocal.getDuration());
		{	
		List<HookRewardInfoProtocolJavaBean> list = new ArrayList<HookRewardInfoProtocolJavaBean>();
		for (HookRewardInfoProtocol JavaBean : protocal.getRewardInfoList()) {
			HookRewardInfoProtocolJavaBean protocol = new HookRewardInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setRewardInfo(list);
		}
		{	
		List<HookConsumeInfoProtocolJavaBean> list = new ArrayList<HookConsumeInfoProtocolJavaBean>();
		for (HookConsumeInfoProtocol JavaBean : protocal.getConsumeInfoList()) {
			HookConsumeInfoProtocolJavaBean protocol = new HookConsumeInfoProtocolJavaBean();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.setConsumeInfo(list);
		}
	}

	@Override
	public HookInfoProtocol javaBeanToProtocol() {
		Builder newBuilder = HookInfoProtocol.newBuilder();
		newBuilder.setHookId(this.getHookId());
		newBuilder.setHookName(this.getHookName());
		newBuilder.setDuration(this.getDuration());
		{
			List<HookRewardInfoProtocol> list = new ArrayList<HookRewardInfoProtocol>();
			for (HookRewardInfoProtocolJavaBean JavaBean : this.getRewardInfo()) {
			list.add((HookRewardInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllRewardInfo(list);
		}
		{
			List<HookConsumeInfoProtocol> list = new ArrayList<HookConsumeInfoProtocol>();
			for (HookConsumeInfoProtocolJavaBean JavaBean : this.getConsumeInfo()) {
			list.add((HookConsumeInfoProtocol) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAllConsumeInfo(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public HookInfoProtocol bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return HookInfoProtocol.parseFrom(bytes);
	}
}