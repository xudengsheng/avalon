package com.avalon.util;

import com.avalon.setting.SystemEnvironment;

public class AkkaPathDecorate {

	public static String getFixSupervisorPath(String address,String identity) {
		return address + SystemEnvironment.AKKA_USER_PATH + identity;
	}
	public static String getLocalFixSubscriberPath(String identity) {
		return  SystemEnvironment.AKKA_USER_PATH + identity;
	}

}
