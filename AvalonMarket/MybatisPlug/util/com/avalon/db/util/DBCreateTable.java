package com.avalon.db.util;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBCreateTable {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable(Collection<Class> tables) {
		String databaseUrl = DBConfig.JDBC_URL;
		ConnectionSource connectionSource;
		try {
			connectionSource = new JdbcConnectionSource(databaseUrl, DBConfig.DB_USER, DBConfig.DB_PASSWORD);
			for (Class class1 : tables) {
				TableUtils.createTableIfNotExists(connectionSource, class1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
