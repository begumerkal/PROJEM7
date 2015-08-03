package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseSign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_sign", catalog = "game3")
public class BaseSign implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short vipLv;
	private Integer month;
	private Short isShow;
	private String award;

	// Constructors

	/** default constructor */
	public BaseSign() {
	}

	/** full constructor */
	public BaseSign(Integer id, Short vipLv, Integer month, Short isShow,
			String award) {
		this.id = id;
		this.vipLv = vipLv;
		this.month = month;
		this.isShow = isShow;
		this.award = award;
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

	@Column(name = "vip_lv", nullable = false)
	public Short getVipLv() {
		return this.vipLv;
	}

	public void setVipLv(Short vipLv) {
		this.vipLv = vipLv;
	}

	@Column(name = "month", nullable = false)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	@Column(name = "is_show", nullable = false)
	public Short getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	@Column(name = "award", nullable = false)
	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}

}