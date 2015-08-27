package com.avalon.core.model;

import akka.actor.Address;

import com.avalon.core.status.GameNodeNetWorkStatus;
import com.google.common.collect.Ordering;

/**
 * 游戏逻辑服务器节点封装对象
 * 
 * @author ZERO
 *
 */
public class ServerNodeMember {

	// 节点的唯一ID
	public final int uid;
	// 节点地址
	public final Address address;
	// 当前节点拥有会话数量
	private int sessionNum = 0;
	// 当前节点网络联通状态
	private GameNodeNetWorkStatus state;

	public ServerNodeMember(int uid, Address address) {
		super();
		this.uid = uid;
		this.address = address;
		this.state = GameNodeNetWorkStatus.UNICOM;
	}

	public int getSessionNum() {
		return sessionNum;
	}

	public void setSessionNum(int sessionNum) {
		this.sessionNum = sessionNum;
	}

	public GameNodeNetWorkStatus getState() {
		return state;
	}

	public void setState(GameNodeNetWorkStatus state) {
		this.state = state;
	}

	public static Ordering<ServerNodeMember> bySessionOrdering = new Ordering<ServerNodeMember>() {
		@Override
		public int compare(ServerNodeMember left, ServerNodeMember right) {
			return left.sessionNum-right.sessionNum;
		}
		
	};

}
