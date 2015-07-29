package com.wyd.channel.service.factory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wyd.channel.service.IThirdConfigService;

public class ServiceManager {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static ServiceManager instance = null;
	private Configuration configuration = null;
	private static ExecutorService httpThreadPool = Executors.newCachedThreadPool();
	private ServiceManager() {
		try {
			configuration = new PropertiesConfiguration("config.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ServiceManager getManager() {
		synchronized (ServiceManager.class) {
			if (null == instance) {
				instance = new ServiceManager();
			}
		}
		return instance;
	}

	public void init() {
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}
	public ExecutorService getHttpThreadPool() {
		return httpThreadPool;
	}

	/**
	 * 第三方渠道配置表
	 * 
	 * @return
	 */
	public IThirdConfigService getThirdConfigService() {
		return (IThirdConfigService) context.getBean("ThirdConfigService");
	}

}