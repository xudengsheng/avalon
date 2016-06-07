package com.avalon.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.db.api.ManagedObject;

/**
 * 检测数据定义文件
 * 
 * @author zhaoxiaolong
 * 
 */
public class DBCheckHandler {

	Logger logger = LoggerFactory.getLogger(DBCheckHandler.class);

	C3P0DataSourceFactory c3p0DataSourceFactory;
	List<String> tables;
	public static Map<String, Long> tableNameMaxId = new HashMap<String, Long>();

	public DBCheckHandler(C3P0DataSourceFactory c3p0DataSourceFactory) {
		this.c3p0DataSourceFactory = c3p0DataSourceFactory;
	}

	@SuppressWarnings("rawtypes")
	public void checkDbAndEntity(Collection<Class<?>> classes) throws SQLException {
		DataSource dataSource = c3p0DataSourceFactory.getDataSource();
		checkMaxId(classes, dataSource);
	}


	@SuppressWarnings("rawtypes")
	private void checkMaxId(Collection<Class<?>> classes, DataSource dataSource) {
		for (Class clazz : classes) {
			if (!ManagedObject.class.isAssignableFrom(clazz)) {
				continue;
			}
			Connection connection;
			ResultSet executeQuery;
			try {
				connection = dataSource.getConnection();
				Statement createStatement = connection.createStatement();
				String tableName = clazz.getSimpleName().toLowerCase();
				executeQuery = createStatement.executeQuery("SHOW COLUMNS FROM " + tableName + " WHERE `Key` = 'pri'");
				if (executeQuery.next()) {
					String priKey = executeQuery.getString(1);
					Statement createStatement1 = connection.createStatement();
					ResultSet executeQuery2 = createStatement1.executeQuery("SELECT " + priKey + " FROM " + tableName
							+ " WHERE " + priKey + " IN (SELECT MAX(" + priKey + ") FROM " + tableName + ")");
					if (executeQuery2.next()) {
						tableNameMaxId.put(clazz.getSimpleName(), executeQuery2.getLong(1));
						logger.info("添加已有键：" + clazz.getSimpleName());
					} else {
						tableNameMaxId.put(clazz.getSimpleName(), 0L);
						logger.info("添加未有键：" + clazz.getSimpleName());

					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
