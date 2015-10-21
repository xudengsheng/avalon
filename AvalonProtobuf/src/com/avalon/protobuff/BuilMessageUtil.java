package com.avalon.protobuff;

import java.io.File;
import java.io.IOException;

import jodd.props.Props;
/**
 * 生成入口
 * @author zero
 *
 */
public class BuilMessageUtil {
	public static void main(String[] args) throws IOException
	{
		Props props=new Props();
		props.load(new File("./conf/app.properties"));
		
		String protonbufSource = props.getValue("Protonbuf");
		ProtonbufReadUtil readUtil=new ProtonbufReadUtil(protonbufSource,props);
		readUtil.searchFile();
		readUtil.readFileInfo();
		
	}
}
