package com.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongo.dao.IEntity;


@Document(collection = "log_msg")
public class LogEntity extends IEntity{

	private int type;
	private int subType;
	private String msg;
	
	public LogEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
