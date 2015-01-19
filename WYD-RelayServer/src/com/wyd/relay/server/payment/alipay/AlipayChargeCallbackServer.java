package com.wyd.relay.server.payment.alipay;

import com.wyd.relay.server.http.JettyServer;
import com.wyd.relay.server.service.factory.ServiceManager;
import com.wyd.relay.server.service.impl.AlipayChargeService;

/**
 * 支付宝充值中转服务处理。
 * 
 * @author guoxuantao
 *	2012-9-20  下午06:08:03
 */
public class AlipayChargeCallbackServer{
	private JettyServer         jettyServer;
	
	public void start() throws Exception {
        // 启动 jetty 服务 加载支付宝第三方支付管理平台
        this.jettyServer = new JettyServer(ServiceManager.getManager().getConfiguration().getString("alipayChargeCallbackIp"), ServiceManager.getManager().getConfiguration().getInt("alipayChargeCallbackPort"), 1, 5);
        this.jettyServer.addServlet(AlipayChargeService.relaySuffix, new AlipayChargeCallbackServlet());
        this.jettyServer.start();
        System.out.println("AlipayCharge HttpServer Started.");
        AlipayChargeService.log.info("AlipayCharge HttpServer Started.");
    }
}
