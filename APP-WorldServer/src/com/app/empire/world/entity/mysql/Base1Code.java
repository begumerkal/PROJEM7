package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1Code entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_code", catalog = "game3")
public class Base1Code implements java.io.Serializable {

	// Fields

	private String code;
	private Integer operatorsId;
	private Integer serverId;
	private Integer type;
	private String spreeData;

	// Constructors

	/** default constructor */
	public Base1Code() {
	}

	/** full constructor */
	public Base1Code(String code, Integer operatorsId, Integer serverId,
			Integer type, String spreeData) {
		this.code = code;
		this.operatorsId = operatorsId;
		this.serverId = serverId;
		this.type = type;
		this.spreeData = spreeData;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 32)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "operators_id", nullable = false)
	public Integer getOperatorsId() {
		return this.operatorsId;
	}

	public void setOperatorsId(Integer operatorsId) {
		this.operatorsId = operatorsId;
	}

	@Column(name = "server_id", nullable = false)
	public Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "spree_data", nullable = false, length = 256)
	public String getSpreeData() {
		return this.spreeData;
	}

	public void setSpreeData(String spreeData) {
		this.spreeData = spreeData;
	}

}