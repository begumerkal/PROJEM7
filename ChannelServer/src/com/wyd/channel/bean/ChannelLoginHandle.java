package com.wyd.channel.bean;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class ChannelLoginHandle {
	 
	private int state;// 0处理中，1处理完
	private Date createTime;
	private ChannelLoginResult loginResult;
	private HashMap<String, String> requestMap;
	
	public ChannelLoginHandle(HashMap<String, String> requestMap) {
		createTime = new Date();
		this.requestMap = requestMap;
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

	public HashMap<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(HashMap<String, String> requestMap) {
		this.requestMap = requestMap;
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
