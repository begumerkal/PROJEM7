package com.wyd.empire.world.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminUser {
	private int userId;
	private int userType;
	private String userName;
	private List<Rights> rightsList = new ArrayList<Rights>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Rights> getRightsList() {
		return rightsList;
	}

	public void setRightsList(List<Rights> rightsList) {
		this.rightsList = rightsList;
	}
}
