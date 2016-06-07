package com.avalon.db.util;

import java.util.Collection;
import java.util.LinkedList;
//import com.example.db.Account;


public class 生成数据库 {

	public static void main(String[] args) throws Exception
	{
		System.out.println("开始创建数据库表");
		DBCreateTable createTable=new DBCreateTable();
		createTable.createTable(生成数据库.getClasss());
		System.out.println("结束创建数据库表");
		
		System.out.println("开始生成查找示例文件");
		DBExampleBuild dbExampleBuild = new DBExampleBuild();
		dbExampleBuild.init();
		dbExampleBuild.regedit(生成数据库.getClasss());
		System.out.println("生成查找示例文件结束");

		System.out.println("开始生成查找Mapper文件");
		DBMapperBuild dbMapperBuild = new DBMapperBuild();
		dbMapperBuild.init();
		dbMapperBuild.regedit(生成数据库.getClasss());
		System.out.println("生成查找Mapper文件结束");

		System.out.println("开始生成查找XML文件");
		DBExampleXMLBuild build = new DBExampleXMLBuild();
		build.init();
		build.regedit(生成数据库.getClasss());
		System.out.println("生成查找XML文件结束");
	}
	
	/**
	 * 需要生成的数据库对象类注册
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Class> getClasss()
	{
		Collection<Class> collection = new LinkedList<Class>();
//		collection.add(Account.class);
//		collection.add(Player.class);
		return collection;
	}

}
