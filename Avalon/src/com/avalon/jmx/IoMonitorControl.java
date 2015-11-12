package com.avalon.jmx;

@ManagedDescription("IoMonitorMXBean")
public class IoMonitorControl extends AvalonMBean<IoMonitorMXBean> {

	protected IoMonitorControl(IoMonitorMXBean managedObject, ManagementService service) {
		super(managedObject, service);
		objectName = service.createObjectName("IoMonitorMXBean", "ReTest");
	}

	@ManagedAnnotation("name")
	@ManagedDescription("Name of the Object")
	public String getName() {
		return "IoMonitorMXBean";
	}

}
