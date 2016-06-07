package com.avalon.protobuff.bean;

public class HelperInfoBean {
	public final String java_class_name;
	public final String proto_class_name;
	public final String namePix;
	public final String java_package;

	public HelperInfoBean(String java_class_name, String proto_class_name, String namePix, String java_package) {
		super();
		this.java_class_name = java_class_name;
		this.proto_class_name = proto_class_name;
		this.namePix = namePix;
		this.java_package = java_package;
	}

	public String getJava_class_name() {
		return java_class_name;
	}

	public String getProto_class_name() {
		return proto_class_name;
	}

	public String getNamePix() {
		return namePix;
	}

	public String getJava_package() {
		return java_package;
	}

}
