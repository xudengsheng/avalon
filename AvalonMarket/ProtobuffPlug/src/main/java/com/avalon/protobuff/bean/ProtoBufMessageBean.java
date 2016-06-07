package com.avalon.protobuff.bean;

import java.util.ArrayList;
import java.util.List;


public class ProtoBufMessageBean {

	private final String name;

	private List<ProtobufFieldType> fieldTypes = new ArrayList<ProtobufFieldType>();

	public ProtoBufMessageBean(String name)
	{
		super();
		this.name = name;
	}

	public List<ProtobufFieldType> getFieldTypes()
	{
		return fieldTypes;
	}

	public void setFieldTypes(List<ProtobufFieldType> fieldTypes)
	{
		this.fieldTypes = fieldTypes;
	}

	public String getName()
	{
		return name;
	}

}
