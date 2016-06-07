package com.avalon.db;

import com.avalon.db.util.DBEnvironment;
import com.avalon.util.PropertiesWrapper;

/**
 * mybatis的配置文件 都是基本的配置，不在提供自动生成数据查询的配置文件
 * 
 * @author xiaolong.zhao1
 *
 */
public class MyBatisConfig {

	private String driver;

	private String url;

	private String username;

	private String password;

	private int minPoolSize;

	private int acquireIncrement;

	private int maxPoolSize;

	public MyBatisConfig(PropertiesWrapper properties) {
		this.driver = properties.getProperty(DBEnvironment.DRIVER);
		this.url = properties.getProperty(DBEnvironment.DB_URL);
		this.username = properties.getProperty(DBEnvironment.DB_USER);
		this.password = properties.getProperty(DBEnvironment.DB_PASSWORD);

		this.minPoolSize = properties.getIntProperty(DBEnvironment.MINPOOLSIZE, 5);
		this.acquireIncrement = properties.getIntProperty(DBEnvironment.ACQUIREINCREMENT, 5);
		this.maxPoolSize = properties.getIntProperty(DBEnvironment.MAXPOOLSIZE, 10);

	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password == null ? "" : password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

}
