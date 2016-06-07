package com.avalon.db.util.jpt;

import com.avalon.db.util.DBUtil;


public class DBExample {

	private String upperName;

	private String lowerName;

	private String type;

	private boolean primitive = true;

	public DBExample() {
		super();
	}

	public DBExample(String name, String type) {
		super();
		this.lowerName = DBUtil.getLoweName(name);
		this.upperName = DBUtil.getUperName(name);
		this.type = type;
	}



	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}

	public String getLowerName() {
		return lowerName;
	}

	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DBExample [upperName=" + upperName + ", lowerName=" + lowerName + ", type=" + type + "]";
	}

	
	public boolean isPrimitive() {
		return primitive;
	}

	
	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

}
