package com.avalon.core.message;

import java.io.Serializable;

public interface ConnectionSessionSupervisorMessage extends Serializable {

	public class CluserSessionMessage implements ConnectionSessionSupervisorMessage {

		private static final long serialVersionUID = 4143946258710301150L;
		public final int uid;
		public final String supervisorName;
		public final String actorId;
		public final Object origins;

		public CluserSessionMessage(int uid, String supervisorName, String actorId, Object origins)
		{
			super();
			this.uid = uid;
			this.supervisorName = supervisorName;
			this.actorId = actorId;
			this.origins = origins;
		}

	}

}
