package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Quartermaster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_quartermaster", catalog = "game3")
public class Base3Quartermaster implements java.io.Serializable {

	// Fields

	private Integer id;
	private Double random;
	private Integer goodsExtId;
	private Integer goodsNum;
	private Integer quality;
	private Integer type;
	private Integer need;

	// Constructors

	/** default constructor */
	public Base3Quartermaster() {
	}

	/** full constructor */
	public Base3Quartermaster(Integer id, Double random, Integer goodsExtId,
			Integer goodsNum, Integer quality, Integer type, Integer need) {
		this.id = id;
		this.random = random;
		this.goodsExtId = goodsExtId;
		this.goodsNum = goodsNum;
		this.quality = quality;
		this.type = type;
		this.need = need;
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

	@Column(name = "random", nullable = false, precision = 22, scale = 0)
	public Double getRandom() {
		return this.random;
	}

	public void setRandom(Double random) {
		this.random = random;
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

	@Column(name = "quality", nullable = false)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "need", nullable = false)
	public Integer getNeed() {
		return this.need;
	}

	public void setNeed(Integer need) {
		this.need = need;
	}

}