package com.avalon.api.internal;

public interface ActorCallBack {

	public void tellMessage(IoMessagePackage messagePackage);

	public void closed();

}
