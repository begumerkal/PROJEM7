package com.wyd.service.server.factory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wyd.service.server.impl.CheckRechargeService;
import com.wyd.service.utils.ThreadPool;




public class ServiceManager {
	ApplicationContext            context       = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "application-scheduling.xml"});
    private static ServiceManager instance      = null;
    private Configuration         configuration = null;
    private CheckRechargeService  checkRechargeService;
    private ThreadPool            httpThreadPool;
    private ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            //充值对账
            this.checkRechargeService = new CheckRechargeService();
            // 包含http任务的线程池
            this.httpThreadPool = new ThreadPool(20);
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
    public IPlayerItemsFromShopService getPlayerItemsFromShopService() {
        return (IPlayerItemsFromShopService) context.getBean(IPlayerItemsFromShopService.SERVICE_BEAN_ID);
    }
    public IPlayerBillService getPlayerBillService() {
        return (IPlayerBillService) context.getBean(IPlayerBillService.SERVICE_BEAN_ID);
    }
    public IPlayerService getPlayerService() {
        return (IPlayerService) context.getBean(IPlayerService.SERVICE_BEAN_ID);
    }
    
    
    public IWorldCupService getWorldCupPointsService() {
        return (IWorldCupService) context.getBean(IWorldCupService.SERVICE_BEAN_ID);
    }
    
    public IConsortiaService getConsortiaService() {
        return (IConsortiaService) context.getBean(IConsortiaService.SERVICE_BEAN_ID);
    }
    
    public IPlayerPetService getPlayerPetService() {
        return (IPlayerPetService) context.getBean(IPlayerPetService.SERVICE_BEAN_ID);
    }
    
    public IAccountService getAccountService() {
        return (IAccountService) context.getBean(IAccountService.SERVICE_BEAN_ID);
    }
    public ITitleService getTitleService() {
        return (ITitleService) context.getBean(ITitleService.SERVICE_BEAN_ID);
    }
    public IMailService getMailService() {
        return (IMailService) context.getBean(IMailService.SERVICE_BEAN_ID);
    }
   
    
    public IMarryService getMarryService() {
        return (IMarryService) context.getBean(IMarryService.SERVICE_BEAN_ID);
    }
    
    public IPlayerSinConsortiaService getPlayerSinConsortiaService() {
        return (IPlayerSinConsortiaService) context.getBean(IPlayerSinConsortiaService.SERVICE_BEAN_ID);
    }
    public CheckRechargeService getCheckRechargeService() {
		return checkRechargeService;
	}
    public ThreadPool getHttpThreadPool() {
        return httpThreadPool;
    }
   
}