package com.avalon.protocol;

import org.junit.Test;

import com.avalon.core.data.AVAObject;
import com.avalon.core.data.IObject;

public class TestProtocolIObject
{
	@Test
	public void testSimple()
	{
		IObject avaObject=AVAObject.newInstance();
		avaObject.putBool("b", true);
		byte[] binary = avaObject.toBinary();
		AVAObject newFromBinaryData = AVAObject.newFromBinaryData(binary);
		System.err.println(newFromBinaryData.getBool("b"));
	}

}
