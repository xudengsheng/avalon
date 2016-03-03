package com.avalon.jmx;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.core.AvalonEngine;

public class ManagementService {

	private static Logger logger = LoggerFactory.getLogger("AvalonEngine");

	static final String DOMAIN = "com.avalon";
	private static final int INITIAL_CAPACITY = 3;

	final AvalonInstanceMXBean instance;
	private final AvalonInstanceMediator instanceMBean;

	public ManagementService(AvalonEngine instance) {
		logger.debug("ManagementService init");
		this.instance = instance;
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		AvalonInstanceMediator instanceMBean;
		try {
			instanceMBean = new AvalonInstanceMediator(instance, this);
			mbs.registerMBean(instanceMBean, instanceMBean.objectName);
		} catch (Exception e) {
			instanceMBean = null;
			logger.warn("AvalonInstanceMediator error",e);
		}
		this.instanceMBean = instanceMBean;
	}

	public AvalonInstanceMediator getInstanceMBean() {
		return instanceMBean;
	}

	public void destroy() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			Set<ObjectName> entries = mbs
					.queryNames(new ObjectName(DOMAIN + ":instance=" + quote(instance.getName()) + ",*"), null);
			for (ObjectName name : entries) {
				if (mbs.isRegistered(name)) {
					mbs.unregisterMBean(name);
				}
			}
		} catch (Exception e) {
		}
	}

	public static void shutdownAll() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			Set<ObjectName> entries = mbs.queryNames(new ObjectName(DOMAIN + ":*"), null);
			for (ObjectName name : entries) {
				if (mbs.isRegistered(name)) {
					mbs.unregisterMBean(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ObjectName createObjectName(String type, String name) {
		Hashtable<String, String> properties = new Hashtable<String, String>(INITIAL_CAPACITY);
		// properties.put("instance", quote(instance.getName()));
		if (type != null) {
			properties.put("type", quote(type));
		}
		if (name != null) {
			properties.put("name", quote(name));
		}
		try {
			return new ObjectName(DOMAIN, properties);
		} catch (MalformedObjectNameException e) {
			throw new IllegalArgumentException();
		}
	}

	public static String quote(String text) {
		return Pattern.compile("[:\",=*?]").matcher(text).find() ? ObjectName.quote(text) : text;
	}

	@Override
	public String toString() {
		return "ManagementService []";
	}
	
	
}
