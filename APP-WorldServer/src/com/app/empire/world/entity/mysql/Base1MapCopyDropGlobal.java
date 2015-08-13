package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapCopyDropGlobal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_copy_drop_global", catalog = "game3")
public class Base1MapCopyDropGlobal implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer goodsExtId;
	private Integer sum;
	private Integer mapLv;
	private Float random;

	// Constructors

	/** default constructor */
	public Base1MapCopyDropGlobal() {
	}

	/** full constructor */
	public Base1MapCopyDropGlobal(Integer id, Integer goodsExtId, Integer sum,
			Integer mapLv, Float random) {
		this.id = id;
		this.goodsExtId = goodsExtId;
		this.sum = sum;
		this.mapLv = mapLv;
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

	@Column(name = "sum", nullable = false)
	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	@Column(name = "map_lv", nullable = false)
	public Integer getMapLv() {
		return this.mapLv;
	}

	public void setMapLv(Integer mapLv) {
		this.mapLv = mapLv;
	}

	@Column(name = "random", nullable = false, precision = 12, scale = 0)
	public Float getRandom() {
		return this.random;
	}

	public void setRandom(Float random) {
		this.random = random;
	}

}