package com.avalon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	private static Logger avalonEngineLogger = LoggerFactory.getLogger("AvalonEngine");
	
	public static void debugLog(String debugInfo){
		if (avalonEngineLogger.isDebugEnabled()) {
			avalonEngineLogger.debug(debugInfo);
		}
	}
}
