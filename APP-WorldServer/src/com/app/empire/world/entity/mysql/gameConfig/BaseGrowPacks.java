package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseGrowPacks entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_grow_packs", catalog = "game3")
public class BaseGrowPacks implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer aidLv;
	private String reward;

	// Constructors

	/** default constructor */
	public BaseGrowPacks() {
	}

	/** full constructor */
	public BaseGrowPacks(Integer id, Integer aidLv, String reward) {
		this.id = id;
		this.aidLv = aidLv;
		this.reward = reward;
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

	@Column(name = "aid_lv", nullable = false)
	public Integer getAidLv() {
		return this.aidLv;
	}

	public void setAidLv(Integer aidLv) {
		this.aidLv = aidLv;
	}

	@Column(name = "reward", nullable = false)
	public String getReward() {
		return this.reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

}