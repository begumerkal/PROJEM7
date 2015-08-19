package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3SkillLv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_skill_lv", catalog = "game3")
public class Base3SkillLv implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lv;
	private Integer costGold;

	// Constructors

	/** default constructor */
	public Base3SkillLv() {
	}

	/** full constructor */
	public Base3SkillLv(Integer id, Integer lv, Integer costGold) {
		this.id = id;
		this.lv = lv;
		this.costGold = costGold;
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

	@Column(name = "cost_gold", nullable = false)
	public Integer getCostGold() {
		return this.costGold;
	}

	public void setCostGold(Integer costGold) {
		this.costGold = costGold;
	}

}