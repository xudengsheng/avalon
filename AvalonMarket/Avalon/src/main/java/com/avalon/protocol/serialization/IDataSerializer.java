package com.avalon.protocol.serialization;

import java.util.List;
import java.util.Map;

import com.avalon.core.data.IArray;
import com.avalon.core.data.IObject;

public interface IDataSerializer
{
	byte[] object2binary(IObject iObject);

	byte[] array2binary(IArray var1);

	IObject binary2object(byte[] var1);

	IArray binary2array(byte[] var1);

	String object2json(Map<String, Object> var1);

	String array2json(List<Object> var1);

	IObject json2object(String var1);

	IArray json2array(String var1);

	IObject pojo2sfs(Object var1);

	Object sfs2pojo(IObject var1);

}
