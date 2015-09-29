package com.avalon.jmx;

public interface EngineMonitorMXBean {

	public void stopEngine();
	
	public double getCpuUsage();
	
	public int transportActorNum();
}
