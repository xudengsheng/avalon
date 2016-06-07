package com.avalon.db.util;

import com.avalon.setting.SystemEnvironment;

public class DBEnvironment {
	/*----------------------------数据库---------------------------------------*/
	public static final String DB_ROOT = SystemEnvironment.APP_ROOT + ".db";
	public static final String DRIVER = DB_ROOT + ".driver";
	public static final String DB_URL = DB_ROOT + ".url";
	public static final String DB_USER = DB_ROOT + ".username";
	public static final String DB_PASSWORD = DB_ROOT + ".password";

	public static final String MINPOOLSIZE = DB_ROOT + ".e.minpoolsize";
	public static final String ACQUIREINCREMENT = DB_ROOT + ".e.acquireincrement";
	public static final String MAXPOOLSIZE = DB_ROOT + ".e.maxpoolsize";
	
	public static final String DB_GAME_JAR =DB_ROOT + ".gamejar.path";
	public static final String DEBUG_DB_PATH = DB_ROOT + ".debug.path";
	public static final String DEBUG_DB_PACKAGE = DB_ROOT + ".debug.package";
	public static final String DEBUG_MYBATIS_PATH = DB_ROOT + ".debug.mybatis.path";
	public static final String DEBUG_MYBATIS_PACKAGE= DB_ROOT + ".debug.mybatis.package";
	public static final String DEBUG_RESOURCE_PATH= DB_ROOT + ".debug.mybatis.xmlpath";
	
	/*----------------------------数据库---------------------------------------*/
}
