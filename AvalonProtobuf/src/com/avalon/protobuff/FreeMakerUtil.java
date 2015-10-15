package com.avalon.protobuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jodd.util.StringUtil;

import com.avalon.protobuff.bean.HelperInfoBean;
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

	public static final String JAVA_BEAN_HELPER_PRFIX = "CodecHelper";

	// 生成PROTOBUF文件尾缀
	public static final String PROTOBUFF_FILE_PRFIX = "Pro";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_CLASS_PRFIX = "Protocol";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_HELPER_CLASS_PRFIX = "helper";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_JAVA_CLASS_PRFIX = "javabean";

	// JAVA文件输出路径
	public static final String JAVA_FILE_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/example/protocol/"
			+ PROTOBUFF_JAVA_CLASS_PRFIX + "/";
	public static final String JAVA_FILE_HELPER_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/example/protocol/"
			+ PROTOBUFF_HELPER_CLASS_PRFIX + "/";

	private Configuration configuration;
	Template template;

	public void init() throws Exception {
		configuration = new Configuration();
		File root = new File("");
		File absoluteFile = root.getAbsoluteFile();
		configuration.setDirectoryForTemplateLoading(new File(absoluteFile + FREE_MAKER_TEMPLATE));

	}

	public void process(ProtoBufFileBean bufFileBean) throws IOException, TemplateException {
		template = configuration.getTemplate("JavaProtocolTransform.ftl");
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
			dataModel.put("namePix", PROTOBUFF_JAVA_CLASS_PRFIX);
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

	public void processHelper(ProtoBufFileBean bufFileBean) throws TemplateException, IOException {
		template = configuration.getTemplate("JavaProtocolHelper.ftl");
		List<ProtoBufMessageBean> protoBufMessageBean = bufFileBean.getProtoBufMessageBean();
		List<HelperInfoBean> formts = new ArrayList<HelperInfoBean>();
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("package", bufFileBean.getOptions().get("java_package") + "." + PROTOBUFF_HELPER_CLASS_PRFIX);
		dataModel.put("protoName", bufFileBean.getName());
		for (ProtoBufMessageBean protoBufMessageBean2 : protoBufMessageBean) {
			dataModel.put("namePix", PROTOBUFF_JAVA_CLASS_PRFIX);
			HelperInfoBean bean = new HelperInfoBean(protoBufMessageBean2.getName() + JAVA_BEAN_PRFIX,
					protoBufMessageBean2.getName(), PROTOBUFF_JAVA_CLASS_PRFIX, bufFileBean.getOptions().get("java_package"));
			formts.add(bean);
		}
		dataModel.put("FieldSeqence", formts);
		Writer out = new OutputStreamWriter(new FileOutputStream(JAVA_FILE_HELPER_OUT_PATH + bufFileBean.getName()
				+ JAVA_BEAN_HELPER_PRFIX + ".java"), "UTF-8");
		template.process(dataModel, out);
	}
}
