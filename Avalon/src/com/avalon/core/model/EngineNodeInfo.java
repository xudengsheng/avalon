package com.avalon.core.model;

import akka.cluster.Member;

import com.avalon.setting.AvalonServerMode;

/**
 * 节点信息
 * @author zero
 *
 */
public class EngineNodeInfo {
	/**
	 * 节点唯一的UUID
	 */
	public String engineUUID;
	/**
	 * 节点的网络信息
	 */
	public	Member member;
	/**
	 * 节点的服务模式
	 */
	public AvalonServerMode serverMode=AvalonServerMode.UNKNOW;
	
}
