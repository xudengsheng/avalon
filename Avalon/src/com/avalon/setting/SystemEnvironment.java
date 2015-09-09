package com.avalon.setting;

public class SystemEnvironment {

	public static final String ENGINE_NAME = "Avalon";
	public static final String APP_ROOT = "com.avalon";
	public static final String APP_NAME = APP_ROOT + ".app.name";
	public static final String APP_ID = APP_ROOT + ".app.id";
	public static final String APP_LISTENER = APP_ROOT + ".app.listener";
	public static final String APP_SESSION_LISTENER = APP_ROOT + ".app.session.listener";
	public static final String AKKA_NAME = APP_ROOT + ".akka.name";
	public static final String AKKA_CONFIG_NAME = APP_ROOT + "akka.config";
	public static final String AKKA_CONFIG_PATH = AKKA_CONFIG_NAME + ".config.path";
	public static final String ENGINE_MODEL = APP_ROOT + ".model";

	public static final String AVALON_NAME = "AVALON_ACTOR";
	public static final String AVALON_CLUSTER_NAME = "AVALON_ACTOR_CLUSTER";
	public static final String AVALON_GLOBLE_TASK_NAME = "AVALON_ACTOR_GLOBLE_TASK";
	public static final String AVALON_ENGINE_NAME = "AVALON_ENGINE_ACTOR";

	public static final String NETTY_BOSS_GROUP_NUM = APP_ROOT + "netty.bossgroup";
	public static final String NETTY_WORKER_GROUP_NUM = APP_ROOT + "netty.workergroup";
	public static final String NETTY_BACKLOG = APP_ROOT + "netty.backlog";

	public static final String NETTY_SERVER_NAME = APP_ROOT + ".netty.server.name";

	public static final String TCP_PROT = APP_ROOT + ".tcp.port";
	public static final String NETTY_HTTP_PORT = "netty.http.port";
	public static final String MINA_SERVER_NAME = APP_ROOT + ".mina.server.name";

}
