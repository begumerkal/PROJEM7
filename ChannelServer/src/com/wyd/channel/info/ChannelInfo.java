package com.wyd.channel.info;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 渠道信息
 * 
 * @author doter
 *
 */
public class ChannelInfo {
	private int channel; // 渠道编号
	private HashMap<String, String> requestMap; // 渠道请求参数

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public HashMap<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(HashMap<String, String> requestMap) {
		this.requestMap = requestMap;
	}

 

}
