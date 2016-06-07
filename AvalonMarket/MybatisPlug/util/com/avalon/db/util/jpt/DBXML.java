package com.avalon.db.util.jpt;


public class DBXML {

	String jdbcName;

	String lowerName;

	String upperName;

	boolean pk = false;

	public DBXML(String jdbcName, String name) {
		super();
		this.jdbcName = jdbcName;
		this.lowerName = name;
		this.upperName = name;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
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

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

}
