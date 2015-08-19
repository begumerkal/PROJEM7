package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1TreasureDrop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_treasure_drop", catalog = "game3")
public class Base1TreasureDrop implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer goodsExtId;
	private Integer lv;
	private Integer dropBase;
	private Integer sum;

	// Constructors

	/** default constructor */
	public Base1TreasureDrop() {
	}

	/** full constructor */
	public Base1TreasureDrop(Integer id, Integer goodsExtId, Integer lv,
			Integer dropBase, Integer sum) {
		this.id = id;
		this.goodsExtId = goodsExtId;
		this.lv = lv;
		this.dropBase = dropBase;
		this.sum = sum;
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

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "drop_base", nullable = false)
	public Integer getDropBase() {
		return this.dropBase;
	}

	public void setDropBase(Integer dropBase) {
		this.dropBase = dropBase;
	}

	@Column(name = "sum", nullable = false)
	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

}