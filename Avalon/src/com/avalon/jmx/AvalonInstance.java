package com.avalon.jmx;

import com.avalon.setting.AvalonServerMode;

public interface AvalonInstance {

	String getName();

	void stopEngine();
	
	AvalonServerMode getServerMode();

	int transportActorNum();
}
