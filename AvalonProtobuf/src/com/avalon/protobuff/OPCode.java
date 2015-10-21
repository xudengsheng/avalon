package com.avalon.protobuff;

/**
 * 操作码对象
 * 
 * @author zero
 *
 */
public class OPCode {
	String pname;
	int opcode;

	public OPCode(String pname, int opcode)
	{
		super();
		this.pname = pname;
		this.opcode = opcode;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public int getOpcode()
	{
		return opcode;
	}

	public void setOpcode(int opcode)
	{
		this.opcode = opcode;
	}


	@Override
	public String toString()
	{
		return "OPCode [pname=" + pname + ", opcode=" + opcode + "]";
	}

}
