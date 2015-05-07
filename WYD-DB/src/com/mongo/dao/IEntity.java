package com.mongo.dao;

public abstract class IEntity {

	public long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
