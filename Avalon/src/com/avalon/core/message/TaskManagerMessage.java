package com.avalon.core.message;

import java.io.Serializable;

/**
 * 任务调度信息
 * @author zero
 *
 */
public interface TaskManagerMessage extends Serializable {

	public class createTaskMessage implements TaskManagerMessage {
		
		private static final long serialVersionUID = 4186679889031004209L;

		public final Runnable runnable;
		public final long createTime=System.currentTimeMillis();

		public createTaskMessage(Runnable runnable)
		{
			super();
			this.runnable = runnable;
		}

	}

}
