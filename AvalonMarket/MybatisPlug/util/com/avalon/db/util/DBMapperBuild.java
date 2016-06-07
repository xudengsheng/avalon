package com.avalon.db.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.j256.ormlite.field.DatabaseField;

import jodd.io.findfile.ClassScanner;
import jodd.util.StringUtil;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DBMapperBuild {

	private Configuration configuration;

	@SuppressWarnings("rawtypes")
	final Collection<Class> classes = new LinkedList<Class>();

	public void init() throws IOException {
		configuration = new Configuration();
		File root = new File("");
		File absoluteFile = root.getAbsoluteFile();
		configuration.setDirectoryForTemplateLoading(new File(absoluteFile + DBConfig.FREE_MAKER_TEMPLATE));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Class clazz) throws IOException, TemplateException {
		Template template = configuration.getTemplate(DBConfig.DB_MAPPER_FTL);
		ClassController<?> on = new Mirror().on(clazz);
		AllReflectionHandler<?> reflectAll = on.reflectAll();
		System.out.println("生成转换类:" + clazz.getSimpleName());
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("CLASS_NAME", clazz.getSimpleName());
		dataModel.put("MODEL_PACKAGE",  DBConfig.MODEL_PACKAGE);
		// 获得所有内部字段
		MirrorList<Field> fields = reflectAll.fields();

		for (Field field : fields) {
			for (Annotation fileInfo : field.getAnnotations()) {
				Class<? extends Annotation> annotationType = fileInfo.annotationType();
				if (annotationType.isAssignableFrom(DatabaseField.class)) {
					DatabaseField databaseField = (DatabaseField) fileInfo;
					if (databaseField.id()) {
						dataModel.put("PK_NAME", field.getName());
						if (field.getType().isPrimitive()) {
							if (Byte.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Byte.class.getSimpleName());
							} else if (Short.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Short.class.getSimpleName());
							} else if (Integer.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Integer.class.getSimpleName());
							} else if (Long.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Long.class.getSimpleName());
							} else if (Double.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Double.class.getSimpleName());
							} else if (Boolean.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Boolean.class.getSimpleName());
							} else if (Character.TYPE.isAssignableFrom(field.getType())) {
								dataModel.put("PK_TYPE", Character.class.getSimpleName());
							}
						} else {
							dataModel.put("PK_TYPE", field.getType().getName());
						}
					}
				}
			}

		}
		Writer out = new OutputStreamWriter(
				new FileOutputStream(DBConfig.EXAMPLE_PATH + clazz.getSimpleName() + DBConfig.MAPPER_NAME + ".java"),
				"UTF-8");
		template.process(dataModel, out);
		System.out.println("生成转换类:" + clazz.getSimpleName() + " to " + clazz.getSimpleName() + "完成");
	}

	@SuppressWarnings("rawtypes")
	public void findClass() {
		ClassScanner scanner = new ClassScanner() {

			@Override
			protected void onEntry(EntryData entryData) throws Exception {
				String name = entryData.getName();
				try {
					Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(
							DBConfig.MODEL_PACKAGE + StringUtil.remove(StringUtil.remove(name, "/"), ".java"));

					if (!loadClass.isInterface()) {
						if (StringUtil.isEmpty(loadClass.getSimpleName())
								|| StringUtil.endsWithIgnoreCase(loadClass.getSimpleName(), DBConfig.EXAMPLE_NAME)) {
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
		scanner.scan(DBConfig.GAME_DB_PATH);

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
		DBMapperBuild build = new DBMapperBuild();
		build.init();
		build.findClass();
	}

	@SuppressWarnings("rawtypes")
	public void regedit(Collection<Class> collection) {
		classes.addAll(collection);
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
