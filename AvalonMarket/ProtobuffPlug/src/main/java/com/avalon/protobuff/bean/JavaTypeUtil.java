package com.avalon.protobuff.bean;

import com.google.protobuf.WireFormat.JavaType;

public class JavaTypeUtil {

	public static JavaType getJavaTypeByString(String string) {

		switch (string) {
		case "double":
			return JavaType.DOUBLE;
		case "float":
			return JavaType.FLOAT;
		case "int32":
			return JavaType.INT;
		case "int64":
			return JavaType.LONG;
		case "uint32":
			return JavaType.INT;
		case "uint64":
			return JavaType.LONG;
		case "sint32":
			return JavaType.INT;
		case "sint64":
			return JavaType.LONG;
		case "fixed32":
			return JavaType.INT;
		case "fixed64":
			return JavaType.LONG;
		case "sfixed32":
			return JavaType.INT;
		case "sfixed64":
			return JavaType.LONG;
		case "bool":
			return JavaType.BOOLEAN;
		case "string":
			return JavaType.STRING;
		case "bytes":
			return JavaType.BYTE_STRING;
		case "enum":
			return JavaType.ENUM;
		case "message":
			return JavaType.MESSAGE;
		default:
			return JavaType.MESSAGE;
		}
	}

	public static String getJavaTypeToString(JavaType type) {
		if (type.equals(JavaType.MESSAGE)) {
			return Double.class.getName();
		} else if (type.equals(JavaType.DOUBLE)) {
			return Double.class.getName();
		} else if (type.equals(JavaType.FLOAT)) {
			return Float.class.getName();
		} else if (type.equals(JavaType.INT)) {
			return Integer.class.getName();
		} else if (type.equals(JavaType.LONG)) {
			return Long.class.getName();
		} else if (type.equals(JavaType.BOOLEAN)) {
			return Boolean.class.getName();
		} else if (type.equals(JavaType.STRING)) {
			return String.class.getName();
		} else if (type.equals(JavaType.BYTE_STRING)) {
			return Byte.class.getName();
		} else if (type.equals(JavaType.ENUM)) {
			return Enum.class.getName();
		}
		throw new RuntimeException("Unkown type");
	}
}
