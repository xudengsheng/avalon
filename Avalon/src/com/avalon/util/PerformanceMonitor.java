package com.avalon.util;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class PerformanceMonitor {
	private long lastSystemTime = 0L;
	private long lastProcessCpuTime = 0L;
	OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public synchronized double getCpuUsage() {
		if (this.lastSystemTime == 0L) {
			baselineCounters();
			return 0.0D;
		}
		long systemTime = System.nanoTime();
		long processCpuTime = this.osMxBean.getProcessCpuTime();

		double cpuUsage = (processCpuTime - this.lastProcessCpuTime) / (systemTime - this.lastSystemTime);

		this.lastSystemTime = systemTime;
		this.lastProcessCpuTime = processCpuTime;

		return cpuUsage / this.osMxBean.getAvailableProcessors();
	}

	private void baselineCounters() {
		this.lastSystemTime = System.nanoTime();
		this.lastProcessCpuTime = this.osMxBean.getProcessCpuTime();
	}
}
