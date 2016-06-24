package com.avalon.core.data;

public class DataWrapper
{

	private DataType typeId;
	private Object object;

	public DataWrapper(DataType typeId, Object object)
	{
		this.typeId = typeId;
		this.object = object;
	}

	public DataType getTypeId()
	{
		return typeId;
	}

	public Object getObject()
	{
		return object;
	}
}
