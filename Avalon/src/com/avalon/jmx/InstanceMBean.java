package com.avalon.jmx;

import static com.avalon.jmx.ManagementService.quote;

import java.util.Hashtable;

import com.avalon.core.AvalonEngine;

@ManagedDescription("AvalonInstance")
public class InstanceMBean extends AvalonMBean<AvalonEngine> {
	private static final int INITIAL_CAPACITY = 3;

	protected InstanceMBean(AvalonEngine avalonInstance, ManagementService managementService) {
		super(avalonInstance, managementService);
		createProperties(avalonInstance);
		registerMBeans();
	}

	private void createMBeans(AvalonEngine hazelcastInstance, ManagementService managementService) {

	}

	private void registerMBeans() {

	}

	private void createProperties(AvalonEngine avalonInstance) {
		Hashtable<String, String> properties = new Hashtable<String, String>(INITIAL_CAPACITY);
		properties.put("type", quote("AvalonInstance"));
		properties.put("instance", quote(avalonInstance.getClass().getSimpleName()));
		properties.put("name", quote(avalonInstance.getClass().getSimpleName()));
		setObjectName(properties);
	}

	public AvalonInstance getHazelcastInstance() {
		return managedObject;
	}

	@ManagedAnnotation("name")
	@ManagedDescription("Name of the Instance")
	public String getName() {
		return managedObject.getName();
	}


	@ManagedAnnotation(value = "stopEngine", operation = true)
	@ManagedDescription("stopEngine")
	public void stopEngine() {
		managedObject.stopEngine();
	}

	@ManagedAnnotation(value = "transportActorNum", operation = true)
	@ManagedDescription("transportActorNum")
	public int transportActorNum() {
		return managedObject.transportActorNum();
	}
	@ManagedAnnotation(value = "ServerMode")
	@ManagedDescription("ServerMode")
	public String getServerMode() {
		return managedObject.getServerMode().modeName;
	}
}
