package com.avalon.core.proxy;

import com.avalon.jmx.TransportSupervisorControl;

public class TransportSupervisorProxy implements TransportSupervisorControl {

	private int transportNum;

	public static TransportSupervisorProxy getInstance() {
		return Inner.proxy;
	}

	static class Inner {
		static TransportSupervisorProxy proxy = new TransportSupervisorProxy();
	}

	@Override
	public int getTransportNum() {
		return transportNum;
	}


	public void addTransportNum() {
		this.transportNum += 1;
	}

	public void subTransportNum() {
		this.transportNum -= 1;

	}

}
