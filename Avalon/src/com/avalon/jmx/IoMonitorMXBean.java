package com.avalon.jmx;

/**
 * 网络监控
 * 
 * @author ZERO
 *
 */
public interface IoMonitorMXBean {
	
	public int getSessionNum();

	public boolean disConnect(long sessionId);
}
