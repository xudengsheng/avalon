package com.example.db;

import com.avalon.db.api.ManagedObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "account" )
public class Account implements ManagedObject {

	@Override
	public long getId()
	{
		return pid;
	}

	@DatabaseField(id = true)
	private long pid;

	@DatabaseField(unique = true, index = true)
	private String name;

	@DatabaseField
	private String password;

	@DatabaseField
	private long playerId;

	public void setId(long id)
	{
		this.pid = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public void setPid(long pid)
	{
		this.pid = pid;

	}

	public long getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(long playerId)
	{
		this.playerId = playerId;
	}

}
