package com.avalon.jmx;

import com.avalon.setting.AvalonServerMode;

public interface AvalonInstanceMXBean {

	String getName();

	void stopEngine();
	
	AvalonServerMode getServerMode();
	
	int GateServreNum();
	
	int GameServerNum();

}
