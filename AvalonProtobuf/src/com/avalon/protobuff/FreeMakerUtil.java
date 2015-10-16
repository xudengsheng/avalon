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

import org.apache.commons.io.FileUtils;

import jodd.props.Props;
import jodd.util.StringUtil;

import com.avalon.protobuff.bean.HelperInfoBean;
import com.avalon.protobuff.bean.JavaTypeUtil;
import com.avalon.protobuff.bean.NestedTypes;
import com.avalon.protobuff.bean.ProtoBufFileBean;
import com.avalon.protobuff.bean.ProtoBufMessageBean;
import com.avalon.protobuff.bean.ProtobufFieldType;
import com.google.protobuf.WireFormat.JavaType;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreeMakerUtil {
	// FreeMaker模板所在的文件路径
	public static final String FREE_MAKER_TEMPLATE = "/FreeMakerTemplate";
	// Java文件名称的尾缀
	public static final String JAVA_BEAN_PRFIX = "JavaBean";

	public static final String JAVA_BEAN_HELPER_PRFIX = "CodecHelper";

	public static final String JAVA_BEAN_HANDLER_PRFIX = "Handler";

	// 生成PROTOBUF文件尾缀
	public static final String PROTOBUFF_FILE_PRFIX = "Pro";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_CLASS_PRFIX = "Protocol";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_HELPER_CLASS_PRFIX = "helper";
	//
	public static final String PROTOBUFF_HANDLER_CLASS_PRFIX = "handler";
	// 生成的PROTOBUF的Java类尾缀
	public static final String PROTOBUFF_JAVA_CLASS_PRFIX = "javabean";

	public static final String PROFILE_FILTER = "CS";

	// JAVA文件输出路径
	public static final String JAVA_FILE_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/example/protocol/"
			+ PROTOBUFF_JAVA_CLASS_PRFIX + "/";

	public static final String JAVA_FILE_HELPER_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/example/protocol/"
			+ PROTOBUFF_HELPER_CLASS_PRFIX + "/";

	public static final String JAVA_FILE_HANDLER_OUT_PATH = "/Users/zero/Documents/workspace/AvalonProtobuf/src-gen/com/example/protocol/"
			+ PROTOBUFF_HANDLER_CLASS_PRFIX + "/";

	private Configuration configuration;
	Template template;
	private Props props;

	public void init(Props props) throws Exception {
		configuration = new Configuration();
		File root = new File("");
		File absoluteFile = root.getAbsoluteFile();
		this.props = props;
		String value = props.getValue("FREE_MAKER_TEMPLATE", FREE_MAKER_TEMPLATE);
		configuration.setDirectoryForTemplateLoading(new File(absoluteFile + value));

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
			dataModel.put("fileName", bufFileBean.name + props.getValue("PROTOBUFF_FILE_PRFIX", PROTOBUFF_FILE_PRFIX));
			List<String> importLists = new ArrayList<String>();
			dataModel.put("importFiel", importLists);
			dataModel.put("java_class_name",
					protoBufMessageBean2.getName() + props.getValue("JAVA_BEAN_PRFIX", JAVA_BEAN_PRFIX));
			dataModel.put("protoName", protoBufMessageBean2.getName());
			dataModel.put("needcollection", false);
			dataModel.put("namePix", props.getValue("PROTOBUFF_JAVA_CLASS_PRFIX", PROTOBUFF_JAVA_CLASS_PRFIX));
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
						importLists.add(string + "." + name
								+ props.getValue("PROTOBUFF_FILE_PRFIX", PROTOBUFF_FILE_PRFIX) + ".*");
					}
					javaTypeToString = protobufFieldType.cstomized;
				}

				ProtobufWriteFormt formt = new ProtobufWriteFormt(isCollection, javaTypeToString, primitive, isEnum,
						protobufFieldType.name);
				formts.add(formt);
			}
			dataModel.put("FieldSeqence", formts);
			String name = props.getValue("JAVA_FILE_OUT_PATH", JAVA_FILE_OUT_PATH) + protoBufMessageBean2.getName()
					+ props.getValue("JAVA_BEAN_PRFIX", JAVA_BEAN_PRFIX) + ".java";
			FileUtils.write(new File(name), "");
			Writer out = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
			template.process(dataModel, out);
		}

	}

	public void processHelper(ProtoBufFileBean bufFileBean) throws TemplateException, IOException {
		template = configuration.getTemplate("JavaProtocolHelper.ftl");
		List<ProtoBufMessageBean> protoBufMessageBean = bufFileBean.getProtoBufMessageBean();
		List<HelperInfoBean> formts = new ArrayList<HelperInfoBean>();
		Map<String, Object> dataModel = new HashMap<String, Object>();
		String java_package = bufFileBean.getOptions().get("java_package");
		dataModel.put("package",
				java_package + "." + props.getValue("PROTOBUFF_HELPER_CLASS_PRFIX", PROTOBUFF_HELPER_CLASS_PRFIX));
		dataModel.put("protoName", bufFileBean.getName());
		for (ProtoBufMessageBean protoBufMessageBean2 : protoBufMessageBean) {
			dataModel.put("namePix", props.getValue("PROTOBUFF_JAVA_CLASS_PRFIX", PROTOBUFF_JAVA_CLASS_PRFIX));
			HelperInfoBean bean = new HelperInfoBean(protoBufMessageBean2.getName()
					+ props.getValue("JAVA_BEAN_PRFIX", JAVA_BEAN_PRFIX), protoBufMessageBean2.getName(),
					props.getValue("PROTOBUFF_JAVA_CLASS_PRFIX", PROTOBUFF_JAVA_CLASS_PRFIX), java_package);
			formts.add(bean);
		}
		dataModel.put("FieldSeqence", formts);
		String name = props.getValue("JAVA_FILE_HELPER_OUT_PATH", JAVA_FILE_HELPER_OUT_PATH) + bufFileBean.getName()
				+ props.getValue("JAVA_BEAN_HELPER_PRFIX", JAVA_BEAN_HELPER_PRFIX) + ".java";
		FileUtils.write(new File(name), "");
		Writer out = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
		template.process(dataModel, out);
	}

	public void processHandlerr(ProtoBufFileBean bufFileBean) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		template = configuration.getTemplate("GameLogicHandler.ftl");
		List<ProtoBufMessageBean> protoBufMessageBean = bufFileBean.getProtoBufMessageBean();
		for (ProtoBufMessageBean protoBufMessageBean2 : protoBufMessageBean) {
			if (!protoBufMessageBean2.getName().startsWith(props.getValue("PROFILE_FILTER", PROFILE_FILTER))) {
				continue;
			}
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("fileName", bufFileBean.name);
			String string = bufFileBean.getOptions().get("java_package");
			dataModel.put("package", string);
			dataModel.put("java_class_name", protoBufMessageBean2.getName());

			dataModel.put("beanpfix", props.getValue("JAVA_BEAN_PRFIX", JAVA_BEAN_PRFIX));
			dataModel.put("helperpfix", props.getValue("JAVA_BEAN_HELPER_PRFIX", JAVA_BEAN_HELPER_PRFIX));

			dataModel.put("handler", props.getValue("PROTOBUFF_HANDLER_CLASS_PRFIX", PROTOBUFF_HANDLER_CLASS_PRFIX));
			dataModel.put("protoName", props.getValue("PROTOBUFF_FILE_PRFIX", PROTOBUFF_FILE_PRFIX));
			dataModel.put("namePix", props.getValue("PROTOBUFF_JAVA_CLASS_PRFIX", PROTOBUFF_JAVA_CLASS_PRFIX));
			dataModel.put("helpPix", props.getValue("PROTOBUFF_HELPER_CLASS_PRFIX", PROTOBUFF_HELPER_CLASS_PRFIX));

			String name = props.getValue("JAVA_FILE_HANDLER_OUT_PATH", JAVA_FILE_HANDLER_OUT_PATH) + "/"
					+ bufFileBean.name + "/" + protoBufMessageBean2.getName()
					+ props.getValue("JAVA_BEAN_HANDLER_PRFIX", JAVA_BEAN_HANDLER_PRFIX) + ".java";
			FileUtils.write(new File(name), "");
			Writer out = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
			template.process(dataModel, out);
		}

	}
}
