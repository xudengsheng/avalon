package com.avalon.jmx;

import com.avalon.setting.AvalonServerMode;

public interface AvalonInstanceControl {

	String getName();

	void stopEngine();
	
	AvalonServerMode getServerMode();
	
	int GateServreNum();
	
	int GameServerNum();

}
