package com.avalon.protobuff.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个protobuf文件的信息
 * 
 * @author zero
 *
 */
public class ProtoBufFileBean {

	public final String name;

	private Map<String, String> options = new HashMap<String, String>();

	private List<String> importFiel = new ArrayList<String>();

	private List<ProtoBufMessageBean> protoBufMessageBean = new ArrayList<ProtoBufMessageBean>();

	public ProtoBufFileBean(String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}


	public Map<String, String> getOptions()
	{
		return options;
	}

	public List<String> getImportFiel()
	{
		return importFiel;
	}

	public List<ProtoBufMessageBean> getProtoBufMessageBean()
	{
		return protoBufMessageBean;
	}

}
