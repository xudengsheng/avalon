package com.avalon.core.message;

import java.io.Serializable;

import com.avalon.core.actor.GameEngineActor;

public interface GameEngineMessage extends Serializable {

	public class NodeInfo implements GameEngineMessage {

		private static final long serialVersionUID = -8384317080422337000L;

		public final String remotePath;

		public final int uid;
		
		public final String GEUID=GameEngineActor.GEUID;

		public NodeInfo(String remotePath, int uid)
		{
			super();
			this.remotePath = remotePath;
			this.uid = uid;
		}

	}

}
