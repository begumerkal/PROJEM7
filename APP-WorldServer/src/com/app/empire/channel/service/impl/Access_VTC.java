package com.app.empire.channel.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;

import com.app.empire.world.common.util.HttpClientUtil.MySSLProtocolSocketFactory;

public class Access_VTC {
	/** 登出通知URL */
	private static String LOGOUT_URL = "http://account.bom.goplay.vn/Logout/";
	/** 充值通知URL */
	public static String ConfirmIAP = "http://billing.bom.goplay.vn/ConfirmIAP/";
	/** 苹果KEY */
	public static String APPKEY = "vn.goplay.bombom";
	private static Logger log = Logger.getLogger(Access_VTC.class);

	public void logout(String userid, String username, int roleid, String rolename, int serverid, int level) {
		userid = userid.substring(userid.indexOf("_") + 1);
		String reqUrl = LOGOUT_URL + "?userid=" + userid + "&username=" + username + "&serverid=" + serverid + "&roleid=" + roleid
				+ "&rolename=" + rolename + "&level=" + level;
		log.debug(reqUrl);
		HttpClient client = new HttpClient();
		Protocol myhttp = new Protocol("http", new MySSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("http", myhttp);
		GetMethod method = new GetMethod(reqUrl);
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
		try {
			client.executeMethod(method);
			method.getResponseBodyAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			method.abort();
			method.releaseConnection();
		}
	}

	@SuppressWarnings("unused")
	private int getOSType(int channelid) {
		if (channelid > 1000000 && channelid < 1200001) {
			return 1;
		}
		return 2;
	}

	public static int getCPID(int channelid) {
		return channelid - 1000000;
	}

	/**
	 * 判断是否是越南VTC渠道
	 * 
	 * @param channel
	 * @return
	 */
	public static boolean isVTC(int channel) {
		return channel > 1000000 && channel < 2000000;
	}

	/**
	 * 判断是否是越南VTC渠道
	 * 
	 * @param channel
	 * @return
	 */
	public static boolean isVTC(String channel) {
		return isVTC(Integer.parseInt(channel));
	}

}
