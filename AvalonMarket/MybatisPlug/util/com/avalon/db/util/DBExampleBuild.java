package com.avalon.db.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.avalon.db.util.jpt.DBExample;

import jodd.io.findfile.ClassScanner;
import jodd.util.StringUtil;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DBExampleBuild {


	private Configuration configuration;

	@SuppressWarnings("rawtypes")
	final Collection<Class> classes = new LinkedList<Class>();

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

	public void init() throws Exception {
		try {
			configuration = new Configuration();
			File root = new File("");
			File absoluteFile = root.getAbsoluteFile();
			configuration.setDirectoryForTemplateLoading(new File(absoluteFile + DBConfig.FREE_MAKER_TEMPLATE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Class clazz) throws IOException, TemplateException {
		Template template = configuration.getTemplate(DBConfig.DB_EXAMPLE_FTL);
		ClassController<?> on = new Mirror().on(clazz);
		AllReflectionHandler<?> reflectAll = on.reflectAll();
		System.out.println("生成转换类:" + clazz.getSimpleName());
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("CLASS_NAME", clazz.getSimpleName() + DBConfig.EXAMPLE_NAME);
		dataModel.put("MODEL_PACKAGE",  DBConfig.MODEL_PACKAGE);
		// 获得所有内部字段
		MirrorList<Field> fields = reflectAll.fields();

		List<DBExample> dbExamples = new ArrayList<DBExample>();

		for (Field field : fields) {
			if (field.getType().isPrimitive()) {
				DBExample dbExample = null;
				if (Byte.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Byte.class.getSimpleName());
				} else if (Short.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Short.class.getSimpleName());
				} else if (Integer.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Integer.class.getSimpleName());
				} else if (Long.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Long.class.getSimpleName());
				} else if (Double.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Double.class.getSimpleName());
				} else if (Boolean.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Boolean.class.getSimpleName());
				} else if (Character.TYPE.isAssignableFrom(field.getType())) {
					dbExample = new DBExample(field.getName(), Character.class.getSimpleName());
				}
				dbExamples.add(dbExample);
			} else {
				DBExample dbExample = new DBExample(field.getName(), field.getType().getCanonicalName());
				if (field.getType().isAssignableFrom(String.class)) {
					dbExample.setPrimitive(false);
				}
				dbExamples.add(dbExample);
			}
		}
		dataModel.put("sequence", dbExamples);
		Writer out = new OutputStreamWriter(
				new FileOutputStream(DBConfig.EXAMPLE_PATH + clazz.getSimpleName() + DBConfig.EXAMPLE_NAME + ".java"),
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
					Class<?> loadClass = ClassLoader.getSystemClassLoader()
							.loadClass(DBConfig.MODEL_PACKAGE + StringUtil.remove(StringUtil.remove(name, "/"), ".java"));
					if (!loadClass.isInterface()) {
						if (StringUtil.isEmpty(loadClass.getSimpleName())
								|| StringUtil.endsWithIgnoreCase(loadClass.getSimpleName(), DBConfig.EXAMPLE_NAME)) {
							return;
						}
						if (loadClass.isMemberClass()) {
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
		DBExampleBuild build = new DBExampleBuild();
		build.init();
		build.findClass();
	}

}
