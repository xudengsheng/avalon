package com.avalon.component;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.MissingResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.internal.ComponentRegistry;
import com.avalon.api.internal.IService;
import com.google.common.collect.Sets;


/**
 * 组件管理器的基本实现
 * 
 * @author ZERO
 * 
 */
public class ComponentRegistryImpl implements ComponentRegistry {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 存放组件
	 */
	private LinkedHashSet<IService> componentSet;

	public ComponentRegistryImpl()
	{
		componentSet = Sets.newLinkedHashSet();
	}

	@Override
	public Iterator<IService> iterator()
	{
		return Collections.unmodifiableSet(componentSet).iterator();
	}

	@Override
	public <T> T getComponent(Class<T> type)
	{
		// 目标组件
		Object matchComponent = null;

		for (Object component : componentSet)
		{
			if (type.isAssignableFrom(component.getClass()))
			{
				if (matchComponent != null)
				{
					throw new MissingResourceException("More than one matching component", type.getName(), null);
				}
				matchComponent = component;
			}
		}

		if (matchComponent == null)
		{
			throw new MissingResourceException("No matching components", type.getName(), null);
		}

		return type.cast(matchComponent);
	}

	public void addComponent(IService component)
	{
		componentSet.add(component);
		logger.info("Component add component" + component.toString());
	}

}
