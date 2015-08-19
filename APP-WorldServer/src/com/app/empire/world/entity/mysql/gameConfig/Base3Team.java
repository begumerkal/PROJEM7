package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Team entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_team", catalog = "game3")
public class Base3Team implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private Integer limits;
	private Integer value;

	// Constructors

	/** default constructor */
	public Base3Team() {
	}

	/** full constructor */
	public Base3Team(Integer id, Integer type, Integer limits, Integer value) {
		this.id = id;
		this.type = type;
		this.limits = limits;
		this.value = value;
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

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "limits", nullable = false)
	public Integer getLimits() {
		return this.limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}

	@Column(name = "value", nullable = false)
	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}