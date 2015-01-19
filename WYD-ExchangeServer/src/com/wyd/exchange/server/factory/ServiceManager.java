package com.wyd.exchange.server.factory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.exchange.server.ICodeExchangeService;
import com.wyd.exchange.server.IIntegralService;
import com.wyd.exchange.server.IInviteInfoService;
import com.wyd.exchange.server.impl.BatchService;
import com.wyd.exchange.server.impl.ExchangeService;
import com.wyd.exchange.server.impl.IntegralService;
import com.wyd.exchange.server.impl.InviteInfoService;
public class ServiceManager {
    ApplicationContext            context             = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static ServiceManager instance            = null;
    protected Configuration       configuration       = null;
    private BatchService          batchService        = null;

    private ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            this.batchService = new BatchService();
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
    
    public void initData(){
        IntegralService.getInstance(context).initData();
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public BatchService getBatchService() {
        return batchService;
    }
    
    public ICodeExchangeService getExchangeService() {
        return ExchangeService.getInstance(context);
    }
    
    public IInviteInfoService getInviteInfoService() {
        return InviteInfoService.getInstance(context);
    }
    
    public IIntegralService getIntegralService() {
        return IntegralService.getInstance(context);
    }
}