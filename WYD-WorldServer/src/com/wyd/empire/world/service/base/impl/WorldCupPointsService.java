package com.wyd.empire.world.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.service.factory.ServiceManager;

/**
 * 世界杯调用接口服务
 * 
 * @author zengxc
 */
public class WorldCupPointsService {
	Logger log = Logger.getLogger(WorldCupPointsService.class);
	private static WorldCupPointsService instance = null;
	private String serviceUrl;

	public static WorldCupPointsService getInstance() {
		if (instance == null) {
			instance = new WorldCupPointsService();
		}
		return instance;
	}

	public WorldCupPointsService() {
		this.serviceUrl = getServiceUrl();
	}

	/**
	 * 增加帐号积分
	 * 
	 * @param accountId
	 *            :EFUN帐号，client.username
	 * @param points
	 */
	public void addPoints(String accountId, int points) {
		if (StringUtils.hasText(serviceUrl)) {
			ServiceManager.getManager().getHttpThreadPool().execute(new RequestSerialThread(accountId, points, serviceUrl));
		}
	}

	public class RequestSerialThread implements Runnable {
		private String accountId;
		private int points;
		private String url;

		public RequestSerialThread(String accountId, int points, String url) {
			this.accountId = getAccountId(accountId);
			this.points = points;
			this.url = url;
		}

		public void run() {
			if (!StringUtils.hasText(url)) {
				// 世界杯接口地址为空
				return;
			}
			url = url + "/AddPoints/";
			String key = ".ac-yidu";
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new NameValuePair("accountId", accountId));
			data.add(new NameValuePair("points", points + ""));
			data.add(new NameValuePair("sign", ServiceUtils.getMD5(accountId + points + key)));
			try {
				HttpClientUtil.PostData(url, data);
			} catch (Exception e) {
				log.error("调用世界杯活动接口出错！", e);
			}
		}
	}

	/**
	 * EFHK_1197947＝〉1197947
	 * 
	 * @param accountId
	 * @return
	 */
	private String getAccountId(String accountId) {
		int index = accountId.indexOf("_");
		if (index != -1) {
			return accountId.substring(index + 1);
		}
		return accountId;
	}

	public String getServiceUrl() {
		return ServiceManager.getManager().getConfiguration().getString("worldcupurl");
	}
}