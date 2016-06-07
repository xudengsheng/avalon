package com.zero.example.core;

import com.avalon.api.internal.IService;
import com.avalon.extensions.request.ClientExtension;
import com.avalon.extensions.request.IClientRequestHandler;

public class ExampleClientExtension extends ClientExtension implements IService {

	@Override
	protected void addRequestHandler(int requestId, Class<?> theClass) {
		super.addRequestHandler(requestId, theClass);
	}

	@Override
	protected void addRequestHandler(int requestId, IClientRequestHandler requestHandler) {
		super.addRequestHandler(requestId, requestHandler);
	}

	@Override
	protected void clearAllHandlers() {
		super.clearAllHandlers();
	}

	@Override
	public void init(Object obj) {
		//注册监听的handler 
		HandlerRegisterCenter.registerClientRequestHandler(this);
	}

	@Override
	public void handleMessage(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	

	
}
