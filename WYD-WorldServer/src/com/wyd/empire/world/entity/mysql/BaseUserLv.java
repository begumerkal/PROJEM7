package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseUserLv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_user_lv", catalog = "game3")
public class BaseUserLv implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lv;
	private Integer energyTop;
	private Integer experience;

	// Constructors

	/** default constructor */
	public BaseUserLv() {
	}

	/** full constructor */
	public BaseUserLv(Integer id, Integer lv, Integer energyTop,
			Integer experience) {
		this.id = id;
		this.lv = lv;
		this.energyTop = energyTop;
		this.experience = experience;
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

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "energy_top", nullable = false)
	public Integer getEnergyTop() {
		return this.energyTop;
	}

	public void setEnergyTop(Integer energyTop) {
		this.energyTop = energyTop;
	}

	@Column(name = "experience", nullable = false)
	public Integer getExperience() {
		return this.experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

}