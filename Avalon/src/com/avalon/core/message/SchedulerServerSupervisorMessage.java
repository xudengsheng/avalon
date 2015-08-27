package com.avalon.core.message;

import java.io.Serializable;

import com.avalon.api.CancellableTask;

public interface SchedulerServerSupervisorMessage extends Serializable {

	public class CancelTask implements SchedulerServerSupervisorMessage {

		private static final long serialVersionUID = 1707407755003680335L;

		public final String taskId;

		public CancelTask(String taskId)
		{
			super();
			this.taskId = taskId;
		}

	}

	public class RunTask implements SchedulerServerSupervisorMessage {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6127176435840350220L;
		public final long delay;
		public final long period;
		public final Runnable runnable;
		public final int type;

		public RunTask(Runnable runnable)
		{
			super();
			this.type = 1;
			this.delay = 0;
			this.period = 0;
			this.runnable = runnable;
		}

		public RunTask(long delay, Runnable runnable)
		{
			super();
			this.type = 2;
			this.delay = delay;
			this.period = 0;
			this.runnable = runnable;
		}

		public RunTask(long delay, long period, Runnable runnable)
		{
			super();
			this.type = 3;
			this.delay = delay;
			this.period = period;
			this.runnable = runnable;
		}

	}

	public class RunTaskInfo implements SchedulerServerSupervisorMessage {

		private static final long serialVersionUID = -7933769848996824390L;

		public final CancellableTask cancellableTask;

		public RunTaskInfo(CancellableTask cancellableTask)
		{
			super();
			this.cancellableTask = cancellableTask;
		}

	}
}
