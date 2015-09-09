package com.avalon.core;

import java.io.File;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import jodd.props.Props;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.api.AppListener;
import com.avalon.api.internal.IService;
import com.avalon.api.internal.InternalContext;
import com.avalon.component.ComponentRegistryImpl;
import com.avalon.core.service.GlobleTaskManagerService;
import com.avalon.core.service.SystemInfoService;
import com.avalon.io.netty.NettyHandler;
import com.avalon.io.netty.NettyServer;
import com.avalon.jmx.EngineMonitorMXBean;
import com.avalon.setting.AvalonServerMode;
import com.avalon.setting.SystemEnvironment;
import com.avalon.util.PerformanceMonitor;
import com.avalon.util.PropertiesWrapper;

/**
 * 阿瓦隆，引擎入口
 * 
 * @author ZERO
 *
 */
public class AvalonEngine implements EngineMonitorMXBean {

	private static String name;

	private static Logger logger = LoggerFactory.getLogger(AvalonEngine.class);

	/**
	 * 应用上下文
	 */
	private KernelContext application;

	/**
	 * 系统服务器组件(集合)
	 */
	private final ComponentRegistryImpl systemRegistry;

	/**
	 * 应用逻辑
	 */
	private AppListener listener;

	private AvalonServerMode mode;

	private PropertiesWrapper propertiesWrapper;

	public static String getName()
	{
		return name;
	}

	private PerformanceMonitor performanceMonitor;

	/**
	 * 启动引擎的入口
	 * 
	 * @param props
	 * @throws Exception
	 */
	protected AvalonEngine(Props props) throws Exception
	{
		propertiesWrapper = new PropertiesWrapper(props);

		String modelName = propertiesWrapper.getProperty(SystemEnvironment.ENGINE_MODEL, AvalonServerMode.SERVER_TYPE_SINGLE.modeName);

		mode = AvalonServerMode.getSeverMode(modelName);
		// 组件管理器
		systemRegistry = new ComponentRegistryImpl();
		// 应用上下文
		application = new StartupKernelContext(SystemEnvironment.ENGINE_NAME, systemRegistry, propertiesWrapper,mode);
		// 系统级授权
		createAndStartApplication();
	}

	/**
	 * 创建并启动应用
	 */
	private void createAndStartApplication()
	{
		performanceMonitor = new PerformanceMonitor();
		// 服务的启动
		createServices(name);
		// 创建守护周期任务
		// 上层逻辑的启动
		startApplication(name);
	}

	/**
	 * 创建对上层逻辑的服务
	 * 
	 * @param appName
	 */
	private void createServices(String appName)
	{
		// 系统级组件

		IService avalon = new AvalonProxy();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		//如果是网关和单幅模式需要启动网络服务
		if (mode.equals(AvalonServerMode.SERVER_TYPE_SINGLE) || mode.equals(AvalonServerMode.SERVER_TYPE_GATE))
		{
			IService netty = new NettyServer(propertiesWrapper.getIntProperty(SystemEnvironment.TCP_PROT, 12345), NettyHandler.class);
			try
			{
				mbs.registerMBean(netty, new ObjectName("com.avalon.io.netty:type=NettyServer"));
			} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException
					| MalformedObjectNameException e1)
			{
				e1.printStackTrace();
			}
			systemRegistry.addComponent(netty);
		}

		try
		{
			mbs.registerMBean(this, new ObjectName("com.avalon.core:type=AvalonEngine"));
		} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e1)
		{
			e1.printStackTrace();
		}

		systemRegistry.addComponent(avalon);
		
		GlobleTaskManagerService globleTaskManagerProxy=new GlobleTaskManagerService();
		((StartupKernelContext) application).addManager(globleTaskManagerProxy);

		SystemInfoService systemInfoService=new SystemInfoService();
		((StartupKernelContext) application).addManager(systemInfoService);
		
		systemInfoService.setMode(mode);
		int serverId = propertiesWrapper.getIntProperty(SystemEnvironment.APP_ID, -1);
		systemInfoService.setServierId(serverId);
		
		InternalContext.setManagerLocator(new ManagerLocatorImpl());
		application = new KernelContext(application);
		ContextResolver.setTaskState(application);

		for (Object object : application.serviceComponents)
		{
			if (object instanceof IService)
			{
				try
				{
					((IService) object).init(propertiesWrapper);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		// 相关组件初始化
		for (Object object : application.managerComponents)
		{
			if (object instanceof IService)
			{
				try
				{
					((IService) object).init(propertiesWrapper);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 启动逻辑层
	 * 
	 * @param name2
	 * @param owner
	 */
	private void startApplication(String appName)
	{
		//网关服务不需要启动逻辑部分
		if (!mode.equals(AvalonServerMode.SERVER_TYPE_GATE))
		{
			// 启动上层逻辑应用
			listener = (propertiesWrapper).getClassInstanceProperty(SystemEnvironment.APP_LISTENER, AppListener.class, new Class[] {});
			listener.initialize();
			application.setAppListener(listener);
		}
	
	}

	public static void main(String[] args) throws Exception
	{
		File root = new File("");
		logger.info(root.getAbsolutePath());
		// 确保没有过多的参数
		File config = new File(root.getAbsolutePath() + File.separator + "conf" + File.separator + "app.properties");
		if (!config.exists())
		{
			logger.info("not app.properties int conf");
			System.exit(1);
		}
		// 属性
		Props props = new Props();
		props.load(config);

		name = props.getValue(SystemEnvironment.APP_NAME);

		System.out.println(name);
		// 启动核心
		new AvalonEngine(props);
	}

	public ComponentRegistryImpl getSystemRegistry()
	{
		return systemRegistry;
	}

	public boolean getEnableJMX()
	{
		return false;
	}

	@Override
	public void doSomeThing()
	{
		AvalonProxy component = systemRegistry.getComponent(AvalonProxy.class);
		component.handleMessage("");
	}

	@Override
	public double getCpuUsage()
	{
		return performanceMonitor.getCpuUsage();
	}

	@Override
	public int transportActorNum()
	{
		AvalonProxy component = systemRegistry.getComponent(AvalonProxy.class);
		return component.transportNum();
	}

}
