package com.wyd.channel.bean;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ChannelLoginHandle {
	 
	private int state;// 0处理中，1处理完
	private Date createTime;
	private ChannelLoginResult loginResult;
	private HttpServletRequest request;
	
	public ChannelLoginHandle(HttpServletRequest request) {
 
		createTime = new Date();
		this.request = request;
	}
 
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public ChannelLoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(ChannelLoginResult loginResult) {
		this.loginResult = loginResult;
	}
	
	public String toJSON() {
		String code = "", message = "";
		if (loginResult != null) {
			code = loginResult.getCode();
			message = loginResult.getMessage();
		}
		String result = "{\"state\":" + this.state + ",\"code\":\"" + code + "\",\"message\":\"" + message
				+ "\"}";
		return result.replace("\"{", "{").replace("}\"", "}");
	}
	
}
