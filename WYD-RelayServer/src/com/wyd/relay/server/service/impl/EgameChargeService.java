package com.wyd.relay.server.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.relay.bean.Service;
import com.wyd.relay.server.http.HttpUtils;
import com.wyd.relay.server.service.factory.ServiceManager;
import com.wyd.relay.util.ServiceInfoUtils;

/**
 * 电信爱游戏充值中转服务。
 * <P>
 * 我们在这个模块中只提供给电信爱游戏充值回调地址，并将接收到的信息原封不动的发送到帐户模块(也可发往其它的模块，只需要配置端口和IP即可)。
 * 同时会增加其它的信息一并发送，例如我们需要分区帐户ID。
 * 
 * @author guoxuantao 2012-8-6 下午03:07:59
 */
public class EgameChargeService {
	private static final Logger log = Logger.getLogger("egameLog");

	private static String relaySuffix = "/passport";
	private static String ppmsmSuffix = "/sms/callback";
	private static String ppthirdSuffix = "/third/callback";

	/**
	 * 将从电信爱游戏短信充值同步过来的信息原封不动，再加上角色的分区帐户后发送到其它模块。
	 * 
	 * @param infoMap
	 * @param type
	 *            //0广播地址，1充值平台地址
	 * @throws IOException
	 */
	public void sendSmsChargeInfo(Map<String, Object> infoMap, int type) throws IOException {
		List<Service> serviceList = ServiceManager.getManager().getServiceInfoService().getServiceList();
		for (Service service : serviceList) {
			if (type == service.getType()) {
				String url = "http://" + service.getIp() + ":" + service.getPort();
				if (type == 0) {
					url += relaySuffix;
				} else {
					url += "/" + service.getApp() + ppmsmSuffix;
				}
				String ret = HttpUtils.URLGet(url, infoMap);
				if ("success".equals(ret)) {
					break;
				}
			}
		}
	}

	/**
	 * 将从电信爱游戏第三方充值同步过来的信息原封不动，再加上角色的分区帐户发送到其它的模块。
	 * 
	 * @param infoMap
	 * @param type
	 *            //0广播地址，1充值平台地址
	 * @throws IOException
	 */
	public String sendThirdChargeInfo(Map<String, Object> infoMap, int type) throws IOException {
		String ret = "fail";
		List<Service> serviceList = ServiceManager.getManager().getServiceInfoService().getServiceList();
		for (Service service : serviceList) {
			if (type == service.getType()) {
				String url = "http://" + service.getIp() + ":" + service.getPort();
				if (type == 0) {
					url += relaySuffix;
					ret = HttpUtils.URLGet(url, infoMap);
					if ("success".equals(ret)) {
						break;
					}
				} else {
					url += "/" + service.getApp() + ppthirdSuffix;
					HttpUtils.URLGet(url, infoMap);
					return "success";
				}
			}
		}
		return ret;
	}

	/**
	 * 爱游戏短信回调新接口
	 * 
	 * @param infoMap
	 */
	public boolean sendSmsChargeInfo(Map<String, Object> infoMap) throws IOException {
		List<Service> serviceList = ServiceManager.getManager().getServiceInfoService().getServiceList();
		for (Service service : serviceList) {
			if (1 == service.getType()) {
				String strUrl = "http://" + service.getIp() + ":" + service.getPort();
				strUrl += "/" + service.getApp() + ppmsmSuffix;
				strUrl += "?" + HttpUtils.getUrl(infoMap);
				log.debug("strtTotalURL:" + strUrl);
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setUseCaches(false);
				HttpURLConnection.setFollowRedirects(true);
				if (con.getHeaderField("cpparam") != null) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 爱游戏SDK V4.0.1.0 把接收到的信息原样传到支付平台
	 * 
	 * @param strParam
	 * @return
	 * @author zengxc 2014-08-12
	 */
	public String sendThirdChargeSDK4(String strParam) {
		String result = "";
		String url = "http://pay2.zhwyd.com/wydpay/third/callback";
		// String url="http://192.168.1.216:16888/wydpay/third/callback";
		Map<String, Object> paramMap = ServiceInfoUtils.createMap(strParam);
		try {
			result = HttpUtils.URLGet(url, paramMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * 爱游戏SDK V4.0.1.0 把接收到的信息原样传到支付平台
	 * 
	 * @param strParam
	 * @return
	 * @author zengxc 2014-08-12
	 */
	public String sendCheck(String strParam){
		String result = "";
		String url = "http://pay2.zhwyd.com/wydpay/sms/callback";
		// String url="http://192.168.1.216:16888/wydpay/third/callback";
		Map<String, Object> paramMap = ServiceInfoUtils.createMap(strParam);
		// callback 为发钻石流程
		if("callback".equals(paramMap.get("method"))){
			url = "http://pay2.zhwyd.com/wydpay/third/callback";
		}
		try {
			result = HttpUtils.URLGet(url, paramMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
		
	}
}
