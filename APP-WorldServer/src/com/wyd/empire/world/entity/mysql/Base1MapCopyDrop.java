package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapCopyDrop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_copy_drop", catalog = "game3")
public class Base1MapCopyDrop implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer copyId;
	private Integer goodsExtId;
	private Integer sum;
	private String random;

	// Constructors

	/** default constructor */
	public Base1MapCopyDrop() {
	}

	/** full constructor */
	public Base1MapCopyDrop(Integer id, Integer copyId, Integer goodsExtId,
			Integer sum, String random) {
		this.id = id;
		this.copyId = copyId;
		this.goodsExtId = goodsExtId;
		this.sum = sum;
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

	@Column(name = "copy_id", nullable = false)
	public Integer getCopyId() {
		return this.copyId;
	}

	public void setCopyId(Integer copyId) {
		this.copyId = copyId;
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

	@Column(name = "random", nullable = false, length = 256)
	public String getRandom() {
		return this.random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

}