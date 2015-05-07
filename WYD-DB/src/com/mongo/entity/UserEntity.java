package com.mongo.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import com.mongo.dao.IEntity;

@Document(collection = "user")
public class UserEntity extends IEntity{
 
	private NameEntity name;
	private int age;
	private int works;
	private Date birth;
	private String password;
	private String regionName;
	private String[] special;

	public NameEntity getName() {
		return name;
	}

	public void setName(NameEntity name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWorks() {
		return works;
	}

	public void setWorks(int works) {
		this.works = works;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String[] getSpecial() {
		return special;
	}

	public void setSpecial(String[] special) {
		this.special = special;
	}

}
