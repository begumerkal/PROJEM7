package com.wyd.channel.info;

import javax.servlet.http.HttpServletRequest;

/**
 * 渠道信息
 * 
 * @author doter
 *
 */
public class ChannelInfo {
	private int channel; // 渠道编号
	private HttpServletRequest request; // 渠道请求参数

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
