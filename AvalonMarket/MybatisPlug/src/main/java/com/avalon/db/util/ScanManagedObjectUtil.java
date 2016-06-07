package com.avalon.db.util;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.db.MybatisDataManagerImpl;
import com.avalon.db.api.ManagedObject;

import jodd.io.findfile.ClassScanner;

public class ScanManagedObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(ScanManagedObjectUtil.class);

	public static void scanManagedObject(final Collection<Class<?>> entitys, boolean debug) {
		logger.info("开始查找DB Class类");
		if (debug) {
			ClassScanner classScanner = new ClassScanner() {

				@Override
				protected void onEntry(EntryData entryData) throws Exception {
					logger.info("发现持久化类文件:" + entryData.getName());
					if (entryData.getName().endsWith(".DS_Store") || entryData.getName().contains("package-info")) {
						return;
					}
					String className = MybatisDataManagerImpl.DB_PACKAGE+"." + entryData.getName();
					Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(className);
					if (ManagedObject.class.isAssignableFrom(loadClass)) {
						entitys.add(loadClass);
					}
				}
			};
			classScanner.setIncludeResources(true);
			classScanner.scan(MybatisDataManagerImpl.DB_PATH);
			logger.info("结束查找DB Class类");
		} else {
			ClassScanner classScanner = new ClassScanner() {

				@Override
				protected void onEntry(EntryData entryData) throws Exception {
					if (!entryData.getName().startsWith(MybatisDataManagerImpl.DB_PACKAGE)) {
						return;
					}
					logger.info("发现持久化类文件:" + entryData.getName());
					String className = entryData.getName();
					Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(className);
					if (ManagedObject.class.isAssignableFrom(loadClass)) {
						entitys.add(loadClass);
					}
				}
			};
			classScanner.setIncludeResources(true);
			classScanner.scan("./deploy/game.jar");
			logger.info("结束查找DB Class类");
		}
	}

	public static void findMybatisMapper(final Map<String, Class<?>> mybatisMapper, boolean debug) {
		if (debug) {
			ClassScanner classScanner = new ClassScanner() {

				@Override
				protected void onEntry(EntryData entryData) throws Exception {
					if (entryData.getName().contains(MybatisDataManagerImpl.FILTER_$)
							|| entryData.getName().endsWith(MybatisDataManagerImpl.FILTER_EXAMPLE)
							|| entryData.getName().endsWith(MybatisDataManagerImpl.FILTER_XML)
							|| entryData.getName().contains(MybatisDataManagerImpl.FILTER_PACKAGE_INFO)) {
						return;
					}
					logger.info("发现持久化类操作Mapper文件:" + entryData.getName());
					String className = MybatisDataManagerImpl.MYBATIS_PACKAGE+"."  + entryData.getName();
					Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(className);
					mybatisMapper.put(entryData.getName(), loadClass);
				}
			};
			classScanner.setIncludeResources(true);
			classScanner.scan(MybatisDataManagerImpl.MYBATIS_PATH);
		} else {
			ClassScanner classScanner = new ClassScanner() {

				@Override
				protected void onEntry(EntryData entryData) throws Exception {
					if (!entryData.getName().startsWith(MybatisDataManagerImpl.MYBATIS_PACKAGE)) {
						return;
					}
					if (entryData.getName().contains(MybatisDataManagerImpl.FILTER_$)
							|| entryData.getName().endsWith(MybatisDataManagerImpl.FILTER_EXAMPLE)
							|| entryData.getName().endsWith(MybatisDataManagerImpl.FILTER_XML)
							|| entryData.getName().contains(MybatisDataManagerImpl.FILTER_PACKAGE_INFO)) {
						return;
					}
					logger.info("发现持久化类操作Mapper文件:" + entryData.getName());
					String className = entryData.getName();
					Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(className);
					mybatisMapper.put(entryData.getName(), loadClass);
				}
			};
			classScanner.setIncludeResources(true);
			classScanner.scan(MybatisDataManagerImpl.DEPLOY_GAME_JAR);
		}

	}
}
