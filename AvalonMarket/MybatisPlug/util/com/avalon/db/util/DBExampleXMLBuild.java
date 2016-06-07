package com.avalon.db.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.io.findfile.ClassScanner;
import jodd.util.StringUtil;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;

import com.avalon.db.util.jpt.DBXML;
import com.j256.ormlite.field.DatabaseField;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DBExampleXMLBuild {


	private Configuration configuration;

	@SuppressWarnings("rawtypes")
	final Collection<Class> classes = new LinkedList<Class>();

	public void init() throws Exception {
		configuration = new Configuration();
		File root = new File("");
		File absoluteFile = root.getAbsoluteFile();
		configuration.setDirectoryForTemplateLoading(new File(absoluteFile + DBConfig.FREE_MAKER_TEMPLATE));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Class clazz) throws IOException, TemplateException {
		Template template = configuration.getTemplate( DBConfig.DBXML_FTL);
		ClassController<?> on = new Mirror().on(clazz);
		AllReflectionHandler<?> reflectAll = on.reflectAll();

		String simpleName = clazz.getSimpleName();
		System.out.println("生成转换类:" + simpleName);

		Map<String, Object> dataModel = new HashMap<String, Object>();
		String lower = getLowerName(simpleName);
		dataModel.put("DB_CLASS", lower.toLowerCase());
		dataModel.put("DB_UPPER_CLASS", simpleName);
		String dbAllName = clazz.getName();
		dataModel.put("DB_ALL_NAME", dbAllName);
		dataModel.put("MODEL_PACKAGE",  DBConfig.MODEL_PACKAGE);
		dataModel.put("DB_MAPPER", dbAllName +  DBConfig.MAPPER_NAME);
		dataModel.put("DB_ALL_EXAMPLE_NAME", DBConfig. EXAMPLE_PACKAGE+"." + simpleName + DBConfig. EXAMPLE_NAME);
		// 获得所有内部字段
		MirrorList<Field> fields = reflectAll.fields();

		List<DBXML> dbExamples = new ArrayList<DBXML>();

		String pkName = null;

		pkName = findUKey(dataModel, fields, pkName);

		for (Field field : fields) {
			DBXML dbExample;
			try {
				dbExample = new DBXML(getJDBCtpye(field.getType().getName()).toString(), field.getName());
				if (field.getName().equals(pkName)) {
					dbExample.setPk(true);
				}
				dbExamples.add(dbExample);
			} catch (Exception e) {
			}
		}
		dataModel.put("sequence", dbExamples);
		Writer out = new OutputStreamWriter(new FileOutputStream( DBConfig.MAPPER_PATH + simpleName +  DBConfig.MAPPER_NAME + ".xml"), "UTF-8");
		template.process(dataModel, out);
		System.out.println("生成转换类:" + simpleName + " to " + simpleName + "完成");
	}

	private String findUKey(Map<String, Object> dataModel, MirrorList<Field> fields, String pkName) {
		// 查找主键
		for (Field field : fields) {
			for (Annotation fileInfo : field.getAnnotations()) {
				if (fileInfo.annotationType().isAssignableFrom(DatabaseField.class)) {
					DatabaseField databaseField = (DatabaseField) fileInfo;
					if (databaseField.id()) {
						pkName = field.getName();
						dataModel.put("PK_NAME", pkName);
						dataModel.put("PK_JDBCTYPE", getJDBCtpye(field.getType().getName()));
						if (Byte.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Byte.class.getName());
						} else if (Short.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Short.class.getName());
						} else if (Integer.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Integer.class.getName());
						} else if (Long.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Long.class.getName());
						} else if (Double.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Double.class.getName());
						} else if (Boolean.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Boolean.class.getName());
						} else if (Character.TYPE.isAssignableFrom(field.getType())) {
							dataModel.put("PK_TYPE", Character.class.getName());
						} else {
							dataModel.put("PK_TYPE", field.getType().getName());
						}
						break;
					}
				}
			}
		}
		return pkName;
	}

	private String getLowerName(String simpleName) {
		return StringUtil.toLowerCase(StringUtil.substring(simpleName, 0, 1))
				+ StringUtil.substring(simpleName, 1, simpleName.length());
	}

	public JDBCType getJDBCtpye(String file) {
		if (file.equals("long") || file.equals("java.lang.Long")) {
			return JDBCType.BIGINT;
		}
		if (file.equals("short") || file.equals("java.lang.Short")) {
			return JDBCType.SMALLINT;
		}
		if (file.equals("int") || file.equals("java.lang.Integer")) {
			return JDBCType.INTEGER;
		}
		if (file.equals("float") || file.equals("java.lang.Float")) {
			return JDBCType.FLOAT;
		}
		if (file.equals("double") || file.equals("java.lang.Double")) {
			return JDBCType.DOUBLE;
		}
		if (file.equals("boolean") || file.equals("java.lang.Boolean")) {
			return JDBCType.BIT;
		}
		if (file.equals("byte") || file.equals("java.lang.Byte")) {
			return JDBCType.TINYINT;
		}
		if (file.equals("java.util.Date") || file.equals("java.sql.Date") || file.equals("java.sql.Timestamp")) {
			return JDBCType.DATE;
		}
		if (file.equals("byte[]") || file.equals("[B")) {
			return JDBCType.BLOB;
		}
		if (file.equals("java.lang.String")) {
			return JDBCType.VARCHAR;
		}
		throw new NullPointerException(file);
	}

	@SuppressWarnings("rawtypes")
	public void findClass() {
		ClassScanner scanner = new ClassScanner() {

			@Override
			protected void onEntry(EntryData entryData) throws Exception {
				String name = entryData.getName();
				try {
					Class<?> loadClass = ClassLoader.getSystemClassLoader()
							.loadClass( DBConfig.MODEL_PACKAGE + StringUtil.remove(StringUtil.remove(name, "/"), ".java"));

					if (!loadClass.isInterface()) {
						if (StringUtil.isEmpty(loadClass.getSimpleName())
								|| StringUtil.endsWithIgnoreCase(loadClass.getSimpleName(),  DBConfig.EXAMPLE_NAME)) {
							return;
						}
						classes.add(loadClass);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		scanner.setIncludeResources(true);
		scanner.scan( DBConfig.GAME_DB_PATH);

		for (Class class1 : classes) {
			System.out.println("查找到需要转换的类:" + class1.getSimpleName());
			try {
				process(class1);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		DBExampleXMLBuild build = new DBExampleXMLBuild();
		build.init();
		build.findClass();
	}

	@SuppressWarnings("rawtypes")
	public void regedit(Collection<Class> classs) {
		classes.addAll(classs);
		for (Class class1 : classes) {
			System.out.println("查找到需要转换的类:" + class1.getSimpleName());
			try {
				process(class1);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		}
	}
}
