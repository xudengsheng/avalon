package com.avalon.core.message;

import java.io.Serializable;
import java.util.UUID;

/**
 * 任务调度信息
 * 
 * @author zero
 *
 */
public interface TaskManagerMessage extends Serializable {

	public class createTaskMessage implements TaskManagerMessage {

		private static final long serialVersionUID = 4186679889031004209L;

		public final Runnable runnable;
		public final String ServerUUID;
		public final long delay;
		public final long period;
		public final long createTime = System.currentTimeMillis();
		public final String TaskUid=UUID.randomUUID().toString();

		public createTaskMessage(Runnable runnable, String serverUUID, long delay, long period)
		{
			super();
			this.runnable = runnable;
			ServerUUID = serverUUID;
			this.delay = delay;
			this.period = period;
		}

	}

}
