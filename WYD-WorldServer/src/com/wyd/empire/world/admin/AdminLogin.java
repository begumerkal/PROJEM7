package com.wyd.empire.world.admin;

import java.util.List;

import com.wyd.empire.world.bean.Application;

public class AdminLogin {
	private int userId;
	private List<Application> appList;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Application> getAppList() {
		return appList;
	}

	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}
}
