package com.wyd.serial.server.factory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.serial.server.IPayService;
import com.wyd.serial.server.impl.SerialService;
import com.wyd.serial.utils.ThreadPool;
public class ServiceManager {
    ApplicationContext            context       = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static ServiceManager instance      = null;
    private Configuration         configuration = null;
    private SerialService         serialService;
    private ThreadPool            simpleThreadPool;

    private ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            serialService = new SerialService();
            // 简单任务的线程池
            this.simpleThreadPool = new ThreadPool(10);
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
        serialService.start();
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public IPayService getPayService() {
        return (IPayService) context.getBean("PayService");
    }
    
    public SerialService getSerialService() {
        return serialService;
    }

    public ThreadPool getSimpleThreadPool() {
        return simpleThreadPool;
    }
}