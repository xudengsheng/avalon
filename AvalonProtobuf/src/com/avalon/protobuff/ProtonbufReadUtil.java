package com.avalon.protobuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avalon.protobuff.bean.JavaTypeUtil;
import com.avalon.protobuff.bean.NestedTypes;
import com.avalon.protobuff.bean.ProtoBufFileBean;
import com.avalon.protobuff.bean.ProtoBufMessageBean;
import com.avalon.protobuff.bean.ProtobufFieldType;
import com.google.protobuf.WireFormat.JavaType;

import freemarker.template.TemplateException;
import jodd.io.FileUtil;
import jodd.io.findfile.FindFile;
import jodd.props.Props;
import jodd.util.StringUtil;

/**
 * protobuf文件读取工具
 * 
 * @author zero
 *
 */
public class ProtonbufReadUtil {

	private static final String PROTO = ".proto";

	private final String protobufPath;
	
	public final Props props;

	private List<File> files = new ArrayList<File>();

	public ProtonbufReadUtil(String protobufPath, Props props) {
		super();
		this.protobufPath = protobufPath;
		this.props = props;
	}

	public void searchFile() {
		FindFile ff = new FindFile().setRecursive(true).setIncludeDirs(true).searchPath(protobufPath);
		File f;
		while ((f = ff.nextFile()) != null) {
			if (f.isDirectory() == true) {
				if (f.getName().endsWith(PROTO)) {
					files.add(f);
				}
			} else {
				if (f.getName().endsWith(PROTO)) {
					files.add(f);
				}
			}
		}
	}

	public void readFileInfo() {
		FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
		try {
			freeMakerUtil.init(props);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (File file : files) {
			try {
				ProtoBufFileBean extracted = extracted(file);
				freeMakerUtil.process(extracted);
				freeMakerUtil.processHelper(extracted);
				freeMakerUtil.processHandlerr(extracted);
			} catch (IOException | TemplateException e) {
				e.printStackTrace();
			}
		}
	}

	private ProtoBufFileBean extracted(File file) throws IOException {
		final String[] readLines = FileUtil.readLines(file);
		String name = file.getName();
		name = name.replace(PROTO, "").trim();
		ProtoBufFileBean fileBean = new ProtoBufFileBean(name);
		ProtoBufMessageBean messageBean = null;
		List<ProtoBufMessageBean> beans = fileBean.getProtoBufMessageBean();
		for (int i = 0; i < readLines.length; i++) {
			// 注释信息直接跳过
			String string = readLines[i];
			boolean startsWithIgnoreCase = StringUtil.startsWithIgnoreCase(string, "//");
			if (startsWithIgnoreCase) {
				continue;
			}
			// 导入文件
			boolean importSource = StringUtil.startsWithIgnoreCase(string, "import");
			if (importSource) {
				String substring = string.substring(string.indexOf("import") + 6, string.indexOf(";"));
				String trim = substring.replace("\"", "").trim();
				if (trim.contains(PROTO)) {
					trim = trim.substring(0, trim.indexOf(PROTO));
				}
				fileBean.getImportFiel().add(trim);
				continue;
			}
			boolean options = StringUtil.startsWithIgnoreCase(string, "option");
			if (options) {
				String substring = string.substring(string.indexOf("option") + 6, string.indexOf(";"));
				String[] split = substring.split("=");

				fileBean.getOptions().put(split[0].trim(), split[1].replace("\"", "").trim());
				continue;
			}
			boolean pass = StringUtil.startsWithIgnoreCase(string.trim(), "//");
			if (pass) {
				continue;
			}
			boolean NULL = string.trim().equals("");
			if (NULL) {
				continue;
			}
			boolean messageStart = StringUtil.startsWithIgnoreCase(string, "message");
			if (messageStart) {
				String substring = string.substring(string.indexOf("message") + 7, string.length());
				if (substring.contains("{")) {
					String messageName = substring.replace("{", "");
					messageBean = new ProtoBufMessageBean(messageName.trim());
					beans.add(messageBean);
				} else {
					messageBean = new ProtoBufMessageBean(substring.trim());
					beans.add(messageBean);
				}
			} else {
				if (messageBean != null) {
					if (string.contains("{")) {
						// 去掉大括号
						String substring = string.substring(string.indexOf("{") + 1, string.length());
						// 如果是结尾的情况
						if (substring.contains("}")) {
							substring = string.substring(0, string.indexOf("}"));
							checkStringLine(messageBean, string, substring);
						} else {
							if (substring.trim().equals("")) {
								continue;
							} else {
								checkStringLine(messageBean, string, substring);
							}
						}
					} else {
						// 如果是结尾的情况
						if (string.contains("}")) {
							// 去掉结尾看看有没有数据
							String substring = string.substring(0, string.indexOf("}"));
							if (substring.trim().equals("")) {
								continue;
							} else {
								checkStringLine(messageBean, string, substring);
							}
						} else {
							String substring = string.trim();
							checkStringLine(messageBean, string, substring);
						}
					}
				}
			}
		}
		return fileBean;
	}

	private void checkStringLine(ProtoBufMessageBean messageBean, String string, String substring) {
		if (substring.contains("required")) {
			substring = string.substring(string.indexOf("required") + "required".length(), string.length()).trim();
			addProFieldType(messageBean, substring, NestedTypes.REQUIRED);
		} else if (substring.contains("optional")) {
			substring = string.substring(string.indexOf("optional") + "optional".length(), string.length()).trim();
			addProFieldType(messageBean, substring, NestedTypes.OPTIONAL);
		} else if (substring.contains("repeated")) {
			substring = string.substring(string.indexOf("repeated") + "repeated".length(), string.length()).trim();
			addProFieldType(messageBean, substring, NestedTypes.REPEATED);
		} else if (substring.contains("enum")) {

		} else {
			// TODO UnKnow
		}
	}

	private void addProFieldType(ProtoBufMessageBean messageBean, String substring, NestedTypes nestedTypes) {
		String gtype = substring.substring(0, substring.indexOf(" "));
		String context = substring.substring(substring.indexOf(" "), substring.length()).trim();
		String[] split = context.split("=");
		JavaType javaTypeByString = JavaTypeUtil.getJavaTypeByString(gtype);
		if (javaTypeByString.equals(JavaType.MESSAGE) || javaTypeByString.equals(JavaType.ENUM)) {
			String fieldName = split[0];
			String seq = split[1].substring(0, split[1].indexOf(";")).trim();
			ProtobufFieldType fieldType = new ProtobufFieldType(fieldName, javaTypeByString, nestedTypes, gtype);
			messageBean.getFieldTypes().add(fieldType);
		} else {
			String fieldName = split[0];
			String seq = split[1].substring(0, split[1].indexOf(";")).trim();
			ProtobufFieldType fieldType = new ProtobufFieldType(fieldName, javaTypeByString, nestedTypes);
			messageBean.getFieldTypes().add(fieldType);
		}
	}
}
