package com.avalon.core.message;

import java.io.Serializable;

public interface ConnectionSessionMessage extends Serializable {

	public class HasSenderPathMessage implements ConnectionSessionMessage {

		private static final long serialVersionUID = -4904126255787744156L;
		
		public final int clusterUid;
		
		public final String senderPath;

		public final Object message;

		public HasSenderPathMessage(int clusterUid,String senderPath, Object message) {
			super();
			this.clusterUid=clusterUid;
			this.senderPath = senderPath;
			this.message = message;
		}

	}
	
	public class DirectSessionMessage implements ConnectionSessionMessage {

		private static final long serialVersionUID = 4143946258710301150L;

		public Object origins;

		public DirectSessionMessage(Object origins) {
			super();
			this.origins = origins;
		}

	}
	
	public class LostConnect implements ConnectionSessionMessage {

		private static final long serialVersionUID = 6040366211875133483L;

	}

}
