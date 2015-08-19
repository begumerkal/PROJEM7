package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3BossReward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_boss_reward", catalog = "game3")
public class Base3BossReward implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer goodsExtId;
	private Integer goodsNum;
	private Integer lv;
	private Float random;

	// Constructors

	/** default constructor */
	public Base3BossReward() {
	}

	/** full constructor */
	public Base3BossReward(Integer id, Integer goodsExtId, Integer goodsNum,
			Integer lv, Float random) {
		this.id = id;
		this.goodsExtId = goodsExtId;
		this.goodsNum = goodsNum;
		this.lv = lv;
		this.random = random;
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

	@Column(name = "goods_ext_id", nullable = false)
	public Integer getGoodsExtId() {
		return this.goodsExtId;
	}

	public void setGoodsExtId(Integer goodsExtId) {
		this.goodsExtId = goodsExtId;
	}

	@Column(name = "goods_num", nullable = false)
	public Integer getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "random", nullable = false, precision = 12, scale = 0)
	public Float getRandom() {
		return this.random;
	}

	public void setRandom(Float random) {
		this.random = random;
	}

}