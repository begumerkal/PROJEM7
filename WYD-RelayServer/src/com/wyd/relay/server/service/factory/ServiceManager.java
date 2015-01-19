package com.wyd.relay.server.service.factory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import com.wyd.relay.server.payment.alipay.AlipayChargeCallbackServer;
import com.wyd.relay.server.payment.egame.EgameSmsChargeCallbackServer;
import com.wyd.relay.server.payment.egame.EgameThirdChargeCallbackServer;
import com.wyd.relay.server.service.impl.AlipayChargeService;
import com.wyd.relay.server.service.impl.EgameChargeService;
import com.wyd.relay.server.service.impl.ServiceInfoService;


public class ServiceManager {
    private static ServiceManager  instance        = null;
    protected Configuration        configuration   = null;
    private EgameChargeService     egameChargeService;
    private AlipayChargeService    alipayChargeService;
    private ServiceInfoService     serviceInfoService;
    private ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("configWorld.properties");
            this.egameChargeService = new EgameChargeService();
            this.alipayChargeService = new AlipayChargeService(configuration);
            this.serviceInfoService = new ServiceInfoService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void init() {
    	//启动电信爱游戏短代、其它充值回调接口服务。
    	this.egameSmsChargeServiceStart();
        this.egameThirdChargeServiceStart();
        //支付宝充值回调接口服务
        this.alipayChargeServiceStart();
    }
    
    /**
     * 电信爱游戏短代充值回调服务
     */
    private void egameSmsChargeServiceStart(){
    	try{
			EgameSmsChargeCallbackServer server = new EgameSmsChargeCallbackServer();
			server.start();
		}catch (Exception e){
			e.printStackTrace();
		}
    }
    
    /**
     * 电信爱游戏平台的第三方支付回调服务
     */
    private void egameThirdChargeServiceStart(){
    	try{
			EgameThirdChargeCallbackServer server = new EgameThirdChargeCallbackServer();
			server.start();
		}catch (Exception e){
			e.printStackTrace();
		}
    }
    
    private void alipayChargeServiceStart(){
    	try{
			AlipayChargeCallbackServer server = new AlipayChargeCallbackServer();
			server.start();
		}catch (Exception e){
			e.printStackTrace();
		}
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

    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public EgameChargeService getEgameChargeService(){
    	return this.egameChargeService;
    }
    
    public AlipayChargeService getAlipayChargeService(){
		return this.alipayChargeService;
	}

    public ServiceInfoService getServiceInfoService() {
        return serviceInfoService;
    }
}