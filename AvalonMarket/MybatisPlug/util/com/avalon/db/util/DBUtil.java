package com.avalon.db.util;

import jodd.util.StringUtil;

public class DBUtil {
	public static String getUperName(String name) {
		return StringUtil.toUpperCase(StringUtil.substring(name, 0, 1))
				+ StringUtil.substring(name, 1, name.length());
	}

	public static String getLoweName(String name) {
		return StringUtil.toLowerCase(StringUtil.substring(name, 0, 1))
				+ StringUtil.substring(name, 1, name.length());
	}
}
