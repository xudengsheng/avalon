package com.avalon.core.message;

import com.avalon.api.internal.IoMessage;
import com.avalon.api.message.GetLocationMessage;

public class GetLocationMessageImpl implements GetLocationMessage {


	private final IoMessage message;

	public GetLocationMessageImpl(IoMessage message)
	{
		super();
		this.message = message;
	}


	@Override
	public IoMessage getMessage()
	{
		return message;
	}

}
