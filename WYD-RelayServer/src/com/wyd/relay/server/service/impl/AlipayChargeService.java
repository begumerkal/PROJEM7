package com.wyd.relay.server.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.wyd.relay.server.http.HttpUtils;

/**
 * 支付宝充值中转服务
 * <P>
 * 我们在这个模块中只提供给支付宝充值后，充值平台回调地址，并将接收到的信息原封不动的发送到帐户模块(也可发往其它的模块，只需要配置端口和IP即可)。
 * 同时会增加其它的信息一并发送，例如我们需要分区帐户ID。
 * 
 * @author guoxuantao
 *	2012-9-20  下午05:55:33
 */
public class AlipayChargeService{
	public static final Logger log = Logger.getLogger("alipayLog");
	public static String relaySuffix = "/alipay";
	private static String alipayChargeRelayIp;
	private static String alipayChargeRelayPort;
	
	public AlipayChargeService(Configuration config){
		alipayChargeRelayIp = config.getString("alipayChargeRelayIp");
		alipayChargeRelayPort = config.getString("alipayChargeRelayPort");
	}
	
	/**
	 * 将使用支付宝充值后从充值平台同步过来的信息原封不动，再加上角色的分区帐户后发送到其它模块。
	 * 
	 * @param infoMap
	 * @throws IOException
	 */
	public void sendAlipayChargeInfo(Map<String, Object> infoMap) throws IOException{
		String url = "http://" + alipayChargeRelayIp + ":" + alipayChargeRelayPort + relaySuffix;
		HttpUtils.URLGet(url, infoMap);
	}
	
	/**
	 * 获取接收到支付宝充值回调信息后需要中转的IP及其端口地址。
	 * 
	 * @return
	 */
	public String getAlipayChargeUrl(){
		String url = "http://" + alipayChargeRelayIp + ":" + alipayChargeRelayPort + relaySuffix;
		return url;
	}
	
}
