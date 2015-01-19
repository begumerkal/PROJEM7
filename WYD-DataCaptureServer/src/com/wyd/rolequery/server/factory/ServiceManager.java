package com.wyd.rolequery.server.factory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.rolequery.server.IAccountService;
import com.wyd.rolequery.server.IWorldService;
public class ServiceManager {
    ApplicationContext            context             = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static ServiceManager instance            = null;
    protected Configuration       configuration       = null;
    protected Configuration       configParam       = null;
    //从第几个id开始查注册数据
    private static int registrationStartId;
    //从第几个id开始查登录数据
    private static int loginStartId;
    //从第几个id开始查充值数据
    private static int rechargeStartId;

    private ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            this.configParam = new PropertiesConfiguration("configParam.properties");
            registrationStartId = this.getConfigParam().getInt("registrationStartId");
            loginStartId = this.getConfigParam().getInt("loginStartId");
            rechargeStartId = this.getConfigParam().getInt("rechargeStartId");
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

    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public Configuration getConfigParam() {
		return this.configParam;
	}

	public IAccountService getAccountService() {
        return (IAccountService) context.getBean("AccountService");
    }
    
    public IWorldService getWorldService() {
        return (IWorldService) context.getBean("WorldService");
    }

	public static int getRegistrationStartId() {
		return registrationStartId;
	}

	public  void setRegistrationStartId(int registrationStartId) {
		ServiceManager.registrationStartId = registrationStartId;
		updataComfigParamDataWrite();
	}

	public static int getLoginStartId() {
		return loginStartId;
	}

	public  void setLoginStartId(int loginStartId) {
		ServiceManager.loginStartId = loginStartId;
		updataComfigParamDataWrite();
	}

	public static int getRechargeStartId() {
		return rechargeStartId;
	}

	public  void setRechargeStartId(int rechargeStartId) {
		ServiceManager.rechargeStartId = rechargeStartId;
		updataComfigParamDataWrite();
	}
	 /**
     * 将map中的内容写到配置文件当中
     */
    public void updataComfigParamDataWrite(){
        StringBuffer content = new StringBuffer();
        BufferedWriter output = null;
        try {
            content.append("#从第几个id开始查注册数据");
            content.append("\t\n");
            content.append("registrationStartId = ");
            content.append(ServiceManager.registrationStartId);
            content.append("\t\n");
            content.append("#从第几个id开始查登录数据");
            content.append("\t\n");
            content.append("loginStartId = ");
            content.append(ServiceManager.loginStartId);
            content.append("\t\n");
            content.append("#从第几个id开始查充值数据");
            content.append("\t\n");
            content.append("rechargeStartId = ");
            content.append(ServiceManager.rechargeStartId);
            content.append("\n");
            output = new BufferedWriter(new FileWriter(Thread.currentThread().getContextClassLoader().getResource("configParam.properties").getPath()));
            output.write(content.toString());
//            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}