package com.wyd.empire.gameaccount.service.factory;
import java.io.File;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.empire.gameaccount.service.IAccountService;
import com.wyd.empire.gameaccount.service.IEmpireaccountService;
import com.wyd.empire.gameaccount.service.ISessionService;
import com.wyd.empire.gameaccount.service.impl.SessionService;
import com.wyd.empire.gameaccount.stub.WorldStub;
public class ServiceFactory {
    protected static ServiceFactory instance = null;
    ApplicationContext              context  = new ClassPathXmlApplicationContext("applicationContext.xml");
    private Configuration           configuration;
    private WorldStub               worldStub;
    private ClientListManager       clientListManager;
    private ISessionService         sessionService;

    protected ServiceFactory() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            this.clientListManager = new ClientListManager(new File(Thread.currentThread().getContextClassLoader().getResource("clients.txt").getPath()));
            this.sessionService = new SessionService();
            this.worldStub = new WorldStub(this.configuration, this.sessionService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public IAccountService getAccountService() {
        return (IAccountService) context.getBean("AccountService");
    }
    
    public IEmpireaccountService getEmpireaccountService() {
        return (IEmpireaccountService) context.getBean("EmpireaccountService");
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public ISessionService getSessionService() {
        return sessionService;
    }

    public WorldStub getWorldStub() {
        return worldStub;
    }

    public ClientListManager getClientListManager() {
        return clientListManager;
    }

    public static ServiceFactory getFactory() {
        synchronized (ServiceFactory.class) {
            if (null == instance) {
                instance = new ServiceFactory();
                instance.init();
            }
        }
        return instance;
    }

    private void init() {
        this.getEmpireaccountService().init();
        this.clientListManager.start();
    }
}
