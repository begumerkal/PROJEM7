package com.wyd.empire.nearby.service.factory;
import java.io.File;

//import org.apache.commons.configuration.Configuration;
//import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wyd.empire.nearby.cache.service.MailManager;
import com.wyd.empire.nearby.cache.service.PlayerInfoManager;
import com.wyd.empire.nearby.service.ISessionService;
import com.wyd.empire.nearby.service.base.INearbyService;
import com.wyd.empire.nearby.service.impl.SessionService;
import com.wyd.empire.nearby.stub.SessionStub;
import com.wyd.empire.nearby.util.Configuration;
import com.wyd.empire.nearby.util.ThreadPool;
public class ServiceManager {
    protected static ServiceManager instance = null;
    ApplicationContext              context  = new ClassPathXmlApplicationContext("applicationContext.xml");
    private Configuration           configuration;
    private SessionStub             sessionStub;
    private ClientListManager       clientListManager;
    private ISessionService         sessionService;
    private ThreadPool              simpleThreadPool;
	private PlayerInfoManager 		playerInfoManager;
	private MailManager		    	mailManager;

    protected ServiceManager() {
        try {
//            this.configuration = new PropertiesConfiguration("config.properties");
        	this.configuration = new Configuration(Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath());
            this.clientListManager = new ClientListManager(new File(Thread.currentThread().getContextClassLoader().getResource("clients.txt").getPath()));
            this.sessionService = new SessionService();
            this.sessionStub = new SessionStub(this.configuration);
            // 简单任务的线程池
            this.simpleThreadPool = new ThreadPool(10);
            this.playerInfoManager = new PlayerInfoManager();
            this.mailManager = new MailManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ISessionService getSessionService() {
        return sessionService;
    }

    public SessionStub getSessionStub() {
        return sessionStub;
    }

    public ClientListManager getClientListManager() {
        return clientListManager;
    }

    public static ServiceManager getManager() {
        synchronized (ServiceManager.class) {
            if (null == instance) {
                instance = new ServiceManager();
                instance.init();
            }
        }
        return instance;
    }

    private void init() {
        this.clientListManager.start();
        this.playerInfoManager.init();
        this.mailManager.init();
    }

    public ThreadPool getSimpleThreadPool() {
        return simpleThreadPool;
    }
    
    public INearbyService getNearbyService() {
        return (INearbyService) context.getBean("NearbyService");
    }

	public PlayerInfoManager getPlayerInfoManager() {
		return playerInfoManager;
	}

	public MailManager getMailManager() {
		return mailManager;
	}
}
