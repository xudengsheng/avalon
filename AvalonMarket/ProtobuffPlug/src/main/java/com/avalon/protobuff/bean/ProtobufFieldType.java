package com.avalon.protobuff.bean;

import com.google.protobuf.WireFormat.JavaType;

public class ProtobufFieldType {
	//字段名称
	public final String name;
	//java类型
	public final JavaType javaType;
	//用户自定义
	public final String cstomized;
	//字段类型
	public final NestedTypes nestedTypes;

	public ProtobufFieldType(String name, JavaType javaType, NestedTypes nestedTypes)
	{
		super();
		this.name = name;
		this.javaType = javaType;
		this.nestedTypes = nestedTypes;
		this.cstomized=javaType.toString();
	}

	public ProtobufFieldType(String name,JavaType javaType, NestedTypes nestedTypes,String cstomized)
	{
		super();
		this.name = name;
		this.javaType = javaType;
		this.nestedTypes = nestedTypes;
		this.cstomized=cstomized;
	}
}
