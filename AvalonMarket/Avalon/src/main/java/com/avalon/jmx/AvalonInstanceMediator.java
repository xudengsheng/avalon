package com.avalon.jmx;

import static com.avalon.jmx.ManagementService.quote;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import com.avalon.core.AvalonEngine;
import com.avalon.core.proxy.TransportSupervisorProxy;
import com.avalon.io.IoMonitorImpl;
import com.avalon.setting.AvalonServerMode;

import javassist.CannotCompileException;
import javassist.NotFoundException;

@ManagedDescription("AvalonInstance")
public class AvalonInstanceMediator extends AvalonMBean<AvalonEngine> {
	private static final int INITIAL_CAPACITY = 3;

	protected AvalonInstanceMediator(AvalonEngine avalonInstance, ManagementService managementService) {
		super(avalonInstance, managementService);
		createProperties(avalonInstance);
		createMBeans(managementService);
		registerMBeans();
	}

	private void createMBeans(ManagementService managementService) {
		if (AvalonEngine.mode.equals(AvalonServerMode.SERVER_TYPE_GATE)) {
			IoMonitorImpl impl = new IoMonitorImpl();
			AvalonMBean control = new IoMonitorMediator(impl, managementService);
			register(control);
		}
		TransportSupervisorProxy proxy = TransportSupervisorProxy.getInstance();
		AvalonMBean transport = new TransportSupervisorMediator(proxy, managementService);
		register(transport);
	}

	private void registerMBeans() {

	}

	private void createProperties(AvalonEngine avalonInstance) {
		Hashtable<String, String> properties = new Hashtable<String, String>(INITIAL_CAPACITY);
		properties.put("type", quote("AvalonInstance"));
		// properties.put("instance",
		// quote(avalonInstance.getClass().getSimpleName()));
		properties.put("name", quote(avalonInstance.getClass().getSimpleName()));
		setObjectName(properties);
	}

	public AvalonInstanceMXBean getHazelcastInstance() {
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

	@ManagedAnnotation(value = "ServerMode")
	@ManagedDescription("ServerMode")
	public String getServerMode() {
		return managedObject.getServerMode().modeName;
	}

	@ManagedAnnotation(value = "GameServerNum")
	@ManagedDescription("GameServer Num")
	public int GameServerNum() {
		return managedObject.GameServerNum();
	}

	@ManagedAnnotation(value = "GateServreNum")
	@ManagedDescription("GateServre Num")
	public int GateServreNum() {
		return managedObject.GateServreNum();
	}

	@ManagedAnnotation(value = "reloadClazz", operation = true)
	@ManagedDescription("reload one class")
	public void reloadClazz(String fileName, String clazzName) throws FileNotFoundException, IOException {
		managedObject.reloadClazz(fileName, clazzName);
	}
	
	@ManagedAnnotation(value = "reloadJar", operation = true)
	@ManagedDescription("reload jar in the reload doc")
	public void reloadJar(String fileName) throws FileNotFoundException, IOException {
		managedObject.reloadJar(fileName);
	}
	
	@ManagedAnnotation(value = "reloadMethod", operation = true)
	@ManagedDescription("reload one class method")
	public void reloadMethod(String clazz, String methodName, String context) throws NotFoundException, CannotCompileException, IOException{
		managedObject.reloadMethod(clazz, methodName, context);
	}
}
