package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapPack entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_pack", catalog = "game3")
public class Base1MapPack implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer lv;
	private String goodsA;
	private String goodsB;

	// Constructors

	/** default constructor */
	public Base1MapPack() {
	}

	/** full constructor */
	public Base1MapPack(Integer id, String name, Integer lv, String goodsA,
			String goodsB) {
		this.id = id;
		this.name = name;
		this.lv = lv;
		this.goodsA = goodsA;
		this.goodsB = goodsB;
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

	@Column(name = "name", nullable = false, length = 36)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "goods_a", nullable = false, length = 500)
	public String getGoodsA() {
		return this.goodsA;
	}

	public void setGoodsA(String goodsA) {
		this.goodsA = goodsA;
	}

	@Column(name = "goods_b", nullable = false, length = 500)
	public String getGoodsB() {
		return this.goodsB;
	}

	public void setGoodsB(String goodsB) {
		this.goodsB = goodsB;
	}

}