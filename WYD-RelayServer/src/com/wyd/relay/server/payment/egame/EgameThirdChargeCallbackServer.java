package com.wyd.relay.server.payment.egame;

import org.apache.log4j.Logger;

import com.wyd.relay.server.http.JettyServer;
import com.wyd.relay.server.service.factory.ServiceManager;

/**
 * 提供爱游戏平台第三方支付回调服务。
 * 
 * @author guoxuantao
 *	2012-8-1  下午03:48:14
 */
public class EgameThirdChargeCallbackServer{
	private JettyServer         jettyServer;
    private static final Logger log = Logger.getLogger("egameLog");
    
    public void start() throws Exception {
        // 启动 jetty 服务 加载第三方支付管理平台(如UC平台)
        this.jettyServer = new JettyServer(ServiceManager.getManager().getConfiguration().getString("egameThirdChargeCallbackIp"), ServiceManager.getManager().getConfiguration().getInt("egameThirdChargeCallbackPort"), 1, 5);
        this.jettyServer.addServlet("/passport", new EgameThirdChargeCallbackServlet());
        this.jettyServer.start();
        System.out.println("EgameThirdCharge HttpServer Started.");
        log.info("EgameThirdCharge HttpServer Started.");
    }
}
