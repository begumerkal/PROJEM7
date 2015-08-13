package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseParameter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_parameter", catalog = "game3")
public class BaseParameter implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer json;
	private String parameter;
	private String info;

	// Constructors

	/** default constructor */
	public BaseParameter() {
	}

	/** full constructor */
	public BaseParameter(Integer id, String name, Integer json,
			String parameter, String info) {
		this.id = id;
		this.name = name;
		this.json = json;
		this.parameter = parameter;
		this.info = info;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "json", nullable = false)
	public Integer getJson() {
		return this.json;
	}

	public void setJson(Integer json) {
		this.json = json;
	}

	@Column(name = "parameter", nullable = false, length = 65535)
	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "info", nullable = false, length = 1024)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}