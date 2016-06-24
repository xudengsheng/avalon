package com.avalon.exception;

import java.util.ArrayList;
import java.util.List;

public class AVAErrorData
{
	IErrorCode code;
	List<String> params = new ArrayList<String>();
	public AVAErrorData(IErrorCode code)
	{
		super();
		this.code = code;
	}
	public IErrorCode getCode()
	{
		return code;
	}
	public void setCode(IErrorCode code)
	{
		this.code = code;
	}
	public List<String> getParams()
	{
		return params;
	}
	public void setParams(List<String> params)
	{
		this.params = params;
	}

}
