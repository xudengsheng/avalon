package com.avalon.protobuff;

import jodd.util.StringUtil;

/**
 * protobuf写文件描述
 * 
 * @author zero
 *
 */
public class ProtobufWriteFormt {
	// 是否是集合
	boolean collection;
	// Java类型描述 java.lang.Integer
	String javaFielType;
	// 是否是原始java,例如 int，short
	boolean primitive;

	boolean isenum;
	// 字段名称
	String fieldName;
	// 字段名称,小写开头
	String lowerName;
	// 字段名称,大写开头
	String upperName;
	//转换成javaBean的名称
	String javaBeanName;

	public ProtobufWriteFormt(boolean collection, String javaFielType, boolean primitive, boolean isenum,
			String fieldName) {
		super();
		this.collection = collection;
		if (!isenum && !primitive) {
			this.javaBeanName = javaFielType + FreeMakerUtil.JAVA_BEAN_PRFIX;
		}
		this.javaFielType = javaFielType;
		this.primitive = primitive;
		this.fieldName = fieldName;
		this.isenum = isenum;
		this.lowerName = StringUtil.toLowerCase(StringUtil.substring(fieldName, 0, 1))
				+ StringUtil.substring(fieldName, 1, fieldName.length()).trim();
		this.upperName = StringUtil.toUpperCase(StringUtil.substring(fieldName, 0, 1))
				+ StringUtil.substring(fieldName, 1, fieldName.length()).trim();
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

	public String getJavaFielType() {
		return javaFielType;
	}

	public void setJavaFielType(String javaFielType) {
		this.javaFielType = javaFielType;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getLowerName() {
		return lowerName;
	}

	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}

	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}

	public boolean isIsenum() {
		return isenum;
	}

	public void setIsenum(boolean isenum) {
		this.isenum = isenum;
	}

	public String getJavaBeanName() {
		return javaBeanName;
	}

	public void setJavaBeanName(String javaBeanName) {
		this.javaBeanName = javaBeanName;
	}

}
