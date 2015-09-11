package com.avalon.core.message;

import java.io.Serializable;

import akka.cluster.Member;

import com.avalon.core.actor.GameEngineActor;
import com.avalon.setting.AvalonServerMode;

public interface GameEngineMessage extends Serializable {
	/**
	 * 检测这个节点是否是自己
	 * 
	 * @author zero
	 *
	 */
	public class CheckNodeInfo implements GameEngineMessage {

		private static final long serialVersionUID = -8384317080422337000L;

		public final Member member;

		public final String GEUID = GameEngineActor.GEUID;

		public CheckNodeInfo(Member member)
		{
			super();
			this.member = member;
		}
	}

	/**
	 * 添加一个节点
	 * 
	 * @author zero
	 *
	 */
	public class AddNodeInfo implements GameEngineMessage {

		private static final long serialVersionUID = -8384317080422337000L;

		public final Member member;

		public AddNodeInfo(Member member)
		{
			super();
			this.member = member;
		}

	}

	/**
	 * 同步节点信息
	 * 
	 * @author zero
	 *
	 */
	public class SysNodeInfo implements GameEngineMessage {

		private static final long serialVersionUID = -8384317080422337000L;

		public final int memberUID;

		public final AvalonServerMode serverMode;

		public final String serverUUID;

		public SysNodeInfo(int memberUID, AvalonServerMode serverMode, String serverUUID)
		{
			super();
			this.memberUID = memberUID;
			this.serverMode = serverMode;
			this.serverUUID = serverUUID;
		}

	}
}
