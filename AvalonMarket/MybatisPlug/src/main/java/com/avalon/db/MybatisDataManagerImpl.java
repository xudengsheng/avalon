package com.avalon.db;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.avalon.api.internal.IService;
import com.avalon.core.ContextResolver;
import com.avalon.db.api.IMybatisCommonHandler;
import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;
import com.avalon.db.api.MyBatisDataManager;
import com.avalon.db.exception.NotHasManagedObjectKey;
import com.avalon.db.exception.NotManagedObject;
import com.avalon.db.handler.CachedCommonHandler;
import com.avalon.db.handler.NoCachedCommonHandler;
import com.avalon.db.handler.ProcessingCacheToDBHandler;
import com.avalon.db.status.DBState;
import com.avalon.db.util.DBEnvironment;
import com.avalon.db.util.ScanManagedObjectUtil;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PropertiesWrapper;

import akka.actor.ActorSystem;
import jodd.io.findfile.FindFile;
import scala.concurrent.duration.Duration;

public class MybatisDataManagerImpl implements MyBatisDataManager, IService {

	private String name;

	private static final String DEVELOPMENT = "development";
	public static String DEPLOY_GAME_JAR ;
	public static final String FILTER_PACKAGE_INFO = "package-info";
	public static final String FILTER_XML = ".xml";
	public static final String FILTER_EXAMPLE = "Example";
	public static final String FILTER_$ = "$";

	// 提供给debug时，使用
	public static String DB_PATH;
	public static String DB_PACKAGE;
	public static String MYBATIS_PATH;
	public static String MYBATIS_PACKAGE;
	public static String DEBUG_RESOURCE_PATH = "./resource/db";
	// 数据持久化类
	private Collection<Class<?>> entitys = new LinkedList<Class<?>>();
	// 持久化类操作Mapper文件
	private Map<String, Class<?>> MybatisMapper = new TreeMap<String, Class<?>>();

	// 数据库持久handler
	private Map<String, IMybatisCommonHandler> mybatisHandler = new TreeMap<String, IMybatisCommonHandler>();

	// 缓存绑定
	Map<String, Object> cacheBinddingMap = new TreeMap<String, Object>();
	// 配置
	private MyBatisConfig myBatisConfig;

	TransactionFactory transactionFactory;
	DataSource dataSource;
	Environment environment;
	Configuration configuration;
	C3P0DataSourceFactory c3p0DataSourceFactory;
	DBCheckHandler checkHandler;
	SqlSessionFactory sqlSessionFactory;
	boolean debug, cached;
	int time;

	public MybatisDataManagerImpl(PropertiesWrapper properties) {
		myBatisConfig = new MyBatisConfig(properties);
		debug = properties.getBooleanProperty(SystemEnvironment.DEBUG, false);
		cached = properties.getBooleanProperty(SystemEnvironment.DB_MYBATIS_CACHE, true);
		time = properties.getIntProperty(SystemEnvironment.DB_MYBATIS_CACHE_TIME, 60);
		try {
			this.c3p0DataSourceFactory = new C3P0DataSourceFactory(myBatisConfig);
		} catch (PropertyVetoException e) {

		}
		checkHandler = new DBCheckHandler(c3p0DataSourceFactory);
		DB_PATH = properties.getProperty(DBEnvironment.DEBUG_DB_PATH, "./bin/com/example/db");
		DB_PACKAGE = properties.getProperty(DBEnvironment.DEBUG_DB_PACKAGE, "com.example.db");

		MYBATIS_PATH = properties.getProperty(DBEnvironment.DEBUG_MYBATIS_PATH, "./bin/com/example/db/example");
		MYBATIS_PACKAGE = properties.getProperty(DBEnvironment.DEBUG_MYBATIS_PACKAGE, "com.example.db.example");
		DEBUG_RESOURCE_PATH = properties.getProperty(DBEnvironment.DEBUG_RESOURCE_PATH, "./resource/db");
		DEPLOY_GAME_JAR = properties.getProperty(DBEnvironment.DB_GAME_JAR, "./deploy/game.jar");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void regeditMybatisMapper() {

		transactionFactory = new JdbcTransactionFactory();
		dataSource = c3p0DataSourceFactory.getDataSource();
		environment = new Environment(DEVELOPMENT, transactionFactory, dataSource);
		configuration = new Configuration(environment);
		configuration.setLazyLoadingEnabled(true);
		// 数据库的操作类
		for (Class<?> clazz : entitys) {
			if (ManagedObject.class.isAssignableFrom(clazz)) {
				configuration.getTypeAliasRegistry().registerAlias(clazz);
			}
		}
		// 映射的mapper
		for (Entry<String, Class<?>> entry : MybatisMapper.entrySet()) {
			configuration.addMapper(entry.getValue());
		}

		FindFile findFile = new FindFile().searchPath(DEBUG_RESOURCE_PATH);
		Iterator<File> iterator = findFile.iterator();
		File next = null;
		while (iterator.hasNext()) {
			try {
				next = iterator.next();
				String URI = next.toURI().toString();
				Map<String, XNode> sqlFragments = configuration.getSqlFragments();
				new XMLMapperBuilder(new FileInputStream(next), configuration, URI, sqlFragments).parse();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init(Object obj) {

		// 查找DB文件
		ScanManagedObjectUtil.scanManagedObject(entitys, debug);
		// 注册Mapper文件
		ScanManagedObjectUtil.findMybatisMapper(MybatisMapper, debug);
		regeditMybatisMapper();

		try {
			checkHandler.checkDbAndEntity(entitys);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		addManagerTask(configuration);
		if (cached) {

			Set<Entry<String, IMybatisCommonHandler>> entrySet = mybatisHandler.entrySet();
			Collection<CachedCommonHandler> handlers = new LinkedList<CachedCommonHandler>();
			for (Entry<String, IMybatisCommonHandler> entry : entrySet) {
				handlers.add((CachedCommonHandler) entry.getValue());
			}
			ActorSystem actorSystem = ContextResolver.getActorSystem();
			ProcessingCacheToDBHandler runnable = new ProcessingCacheToDBHandler(handlers);
			actorSystem.scheduler().schedule(Duration.create(10, TimeUnit.SECONDS),
					Duration.create(time, TimeUnit.SECONDS), runnable, actorSystem.dispatcher());
		}
	}

	private void addManagerTask(Configuration configuration) {

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		sqlSessionFactory = builder.build(configuration);

		// 正常注册Mybatis相关任务上边
		for (Entry<String, Class<?>> entry : MybatisMapper.entrySet()) {
			String key = entry.getKey();
			// 得到需要持久化类的名称
			String trim = key.replace("Mapper", "").trim();

			if (trim.contains(".")) {
				trim = trim.substring(trim.lastIndexOf(".") + 1, trim.length());
			}

			MybatisCommonDBHandler myBatisReference = null;
			try {
				Long idSed = DBCheckHandler.tableNameMaxId.get(trim);
				if (cached) {
					myBatisReference = new CachedCommonHandler(sqlSessionFactory, entry.getValue(), idSed);
				} else {
					myBatisReference = new NoCachedCommonHandler(sqlSessionFactory, entry.getValue(), idSed);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			mybatisHandler.put(trim, myBatisReference);
		}
	}

	@Override
	public void destroy(Object obj) {

	}

	@Override
	public void handleMessage(Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	private void checkManagedObject(Object object) {
		if (object instanceof ManagedObject) {

		} else {
			throw new NotManagedObject("not implements ManagedObject");
		}
	}

	@Override
	public <T> ManagedReference<T> createReference(T object) {
		checkManagedObject(object);
		String name = object.getClass().getSimpleName();
		if (mybatisHandler.containsKey(name)) {
			IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
			return dateReferenceHandler.createReference(object);
		} else {
			throw new NotManagedObject("not this handler ManagedObject:" + object.getClass().getSimpleName());
		}
	}

	@Override
	public <T> ManagedReference<T> getReference(Class<T> object, long id) {
		String name = object.getSimpleName();
		if (mybatisHandler.containsKey(name)) {
			IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
			return dateReferenceHandler.getReference(id);
		} else {
			throw new NotHasManagedObjectKey("not this key ManagedObject:" + object.getClass().getSimpleName());
		}
	}

	@Override
	public void removeObject(Object object) {
		checkManagedObject(object);
		String name = object.getClass().getSimpleName();
		if (mybatisHandler.containsKey(name)) {
			IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
			dateReferenceHandler.setDelete((ManagedObject) object);
		}
	}

	@Override
	public void markForUpdate(Object object) {
		checkManagedObject(object);
		String name = object.getClass().getSimpleName();
		if (mybatisHandler.containsKey(name)) {
			if (object instanceof ManagedReference) {
				IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
				dateReferenceHandler.mark((ManagedObject) object);
				return;
			} else if (object instanceof ManagedObject) {
				IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
				dateReferenceHandler.mark((ManagedObject) object);
				return;
			} else {
				throw new RuntimeException("this object not instanceof ManagedObject or ManagedReference");
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCacheBindding(String name) {
		return (T) cacheBinddingMap.get(name);
	}

	@Override
	public void setCacheBinding(String name, Object object) {
		this.cacheBinddingMap.put(name, object);
	}

	@Override
	public void removeCacheBinding(String name) {
		this.cacheBinddingMap.remove(name);
	}

	@Override
	public <T> List<T> executeQuery(MybatisQureyHelper<T> qureyHelper) {
		List<T> executeQuery = qureyHelper.executeQuery(sqlSessionFactory);
		return executeQuery;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> Collection<ManagedReference<T>> getReferences(Class<T> object, MybatisQureyHelper query) {
		String name = object.getSimpleName();
		if (!mybatisHandler.containsKey(name)) {
			throw new RuntimeException("not this handler ManagedObject:" + object.getSimpleName());
		}
		Collection<ManagedReference<T>> result = new LinkedList<ManagedReference<T>>();
		List<?> executeQuery = query.executeQuery(sqlSessionFactory);

		IMybatisCommonHandler dateReferenceHandler = mybatisHandler.get(name);
		for (Object obj : executeQuery) {
			@SuppressWarnings("unchecked")
			ManagedReference<T> createReference = (ManagedReference<T>) dateReferenceHandler.queryCreateReference(obj);
			((MyBatisReference) createReference).changeState(DBState.DB_STATE_NORMAL);
			result.add(createReference);
		}
		return result;
	}

}
