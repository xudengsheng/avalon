package com.avalon.db.util.jpt;

import com.avalon.db.util.DBUtil;


public class FiledDesc {

	String type;
	String lowerName;
	String upperName;
	boolean list;
	boolean primitive=true;
	String proName;
	String javaBean;

	public FiledDesc(String type, String name) {
		super();
		this.type = type;
		this.lowerName = DBUtil.getLoweName(name);
		this.upperName = DBUtil.getUperName(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	
	public boolean isList() {
		return list;
	}
	
	public void setList(boolean list) {
		this.list = list;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getJavaBean() {
		return javaBean;
	}

	public void setJavaBean(String javaBean) {
		this.javaBean = javaBean;
	}

	
	public boolean isPrimitive() {
		return primitive;
	}

	
	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

}
