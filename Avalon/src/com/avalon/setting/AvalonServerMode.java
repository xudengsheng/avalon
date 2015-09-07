package com.avalon.setting;

import java.util.EnumSet;

public enum AvalonServerMode
{

	SERVER_TYPE_SINGLE("SINGLE"),

	SERVER_TYPE_GAME("GAME"),

	SERVER_TYPE_GATE("GATE");

	public static EnumSet<AvalonServerMode> enums = EnumSet.allOf(AvalonServerMode.class);

	private AvalonServerMode(String name)
	{
		this.modeName = name;
	}

	public final String modeName;

	public static AvalonServerMode getSeverMode(String modelName)
	{
		for (AvalonServerMode iterable_element : enums)
		{
			if (modelName.equals(iterable_element.modeName))
			{
				return iterable_element;
			}
		}
		return SERVER_TYPE_SINGLE;
	}

}
