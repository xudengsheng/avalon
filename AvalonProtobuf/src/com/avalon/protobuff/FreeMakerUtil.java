package com.avalon.protobuff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jodd.util.StringUtil;

import com.avalon.protobuff.bean.JavaTypeUtil;
import com.avalon.protobuff.bean.NestedTypes;
import com.avalon.protobuff.bean.ProtoBufFileBean;
import com.avalon.protobuff.bean.ProtoBufMessageBean;
import com.avalon.protobuff.bean.ProtobufFieldType;
import com.google.protobuf.WireFormat.JavaType;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {
	// FreeMaker模板所在的文件路径
	public static final String FREE_MAKER_TEMPLATE = "/FreeMakerTemplate";
	// Java文件名称的尾缀
	public static final String JAVA_BEAN_PRFIX = "JavaBean";

	// 生成PROTOBUF文件尾缀
	public static final String PROTOBUFF_FILE_PRFIX = "Pro";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_CLASS_PRFIX = "Protocol";

	// JAVA文件输出路径
	public static final String JAVA_FILE_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/zero/example/protobuf/";

	private Configuration configuration;
	Template template;

	public void init() throws Exception {
		configuration = new Configuration();
		File root = new File("");
		File absoluteFile = root.getAbsoluteFile();
		configuration.setDirectoryForTemplateLoading(new File(absoluteFile + FREE_MAKER_TEMPLATE));
		template = configuration.getTemplate("JavaProtocolTransform.ftl");
	}

	public void process(ProtoBufFileBean bufFileBean) throws IOException, TemplateException {
		List<String> importFiel = bufFileBean.getImportFiel();
		List<ProtoBufMessageBean> protoBufMessageBean = bufFileBean.getProtoBufMessageBean();
		for (ProtoBufMessageBean protoBufMessageBean2 : protoBufMessageBean) {
			Set<Entry<String, String>> entrySet = bufFileBean.getOptions().entrySet();
			Map<String, Object> dataModel = new HashMap<String, Object>();
			for (Entry<String, String> entry : entrySet) {
				dataModel.put(entry.getKey(), entry.getValue());
			}
			dataModel.put("fileName", bufFileBean.name + PROTOBUFF_FILE_PRFIX);
			List<String> importLists = new ArrayList<String>();
			dataModel.put("importFiel", importLists);
			dataModel.put("java_class_name", protoBufMessageBean2.getName() + JAVA_BEAN_PRFIX);
			dataModel.put("protoName", protoBufMessageBean2.getName());
			dataModel.put("needcollection", false);
			List<ProtobufFieldType> fieldTypes = protoBufMessageBean2.getFieldTypes();
			List<ProtobufWriteFormt> formts = new ArrayList<ProtobufWriteFormt>();
			for (ProtobufFieldType protobufFieldType : fieldTypes) {
				boolean isCollection = protobufFieldType.nestedTypes.equals(NestedTypes.REPEATED);
				boolean isEnum = protobufFieldType.javaType.equals(JavaType.ENUM);
				boolean primitive = !protobufFieldType.javaType.equals(JavaType.MESSAGE) && !isEnum;
				if (isCollection) {
					dataModel.put("needcollection", true);
				}
				String javaTypeToString;
				if (primitive) {
					javaTypeToString = JavaTypeUtil.getJavaTypeToString(protobufFieldType.javaType);
				} else {
					String string = bufFileBean.getOptions().get("java_package");
					for (String name : importFiel) {
						name = StringUtil.toUpperCase(StringUtil.substring(name, 0, 1))
								+ StringUtil.substring(name, 1, name.length()).trim();
						importLists.add(string + "." + name + PROTOBUFF_FILE_PRFIX + ".*");
					}
					javaTypeToString = protobufFieldType.cstomized;
				}

				ProtobufWriteFormt formt = new ProtobufWriteFormt(isCollection, javaTypeToString, primitive, isEnum,
						protobufFieldType.name);
				formts.add(formt);
			}
			dataModel.put("FieldSeqence", formts);
			Writer out = new OutputStreamWriter(new FileOutputStream(JAVA_FILE_OUT_PATH
					+ protoBufMessageBean2.getName() + JAVA_BEAN_PRFIX + ".java"), "UTF-8");
			template.process(dataModel, out);
			System.out.println("build File " + protoBufMessageBean2.getName());
		}

	}
}
