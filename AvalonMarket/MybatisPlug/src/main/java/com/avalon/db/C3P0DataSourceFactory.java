package com.avalon.db;


import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0的数据库连接池，为Mybatis使用的
 * 
 * @author zhaoxiaolong
 * 
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

	public C3P0DataSourceFactory(MyBatisConfig config) throws PropertyVetoException {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setDriverClass(config.getDriver());
		comboPooledDataSource.setJdbcUrl(config.getUrl());
		comboPooledDataSource.setUser(config.getUsername());
		comboPooledDataSource.setPassword(config.getPassword());
		comboPooledDataSource.setMinPoolSize(config.getMinPoolSize());
		comboPooledDataSource.setAcquireIncrement(config.getAcquireIncrement());
		comboPooledDataSource.setMaxPoolSize(config.getMaxPoolSize());
		this.dataSource = (DataSource) comboPooledDataSource;
	}

}
