package com.avalon.setting;

import java.util.EnumSet;
/**
 * 服务器状态的枚举
 * @author zero
 *
 */
public enum AvalonServerMode
{
	/**
	 * 单服务器模式
	 */
	SERVER_TYPE_SINGLE("SINGLE"),
	/**
	 * 逻辑服务器模式
	 */
	SERVER_TYPE_GAME("GAME"),
	/**
	 * 网关服务器模式
	 */
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
