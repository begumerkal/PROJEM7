package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_everydayreward database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_viprate")
public class VipRate implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer saleRate;
	private Integer expRate;
	private Integer strongRate;
	private Integer bossRate;

	public VipRate() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "saleRate", precision = 10)
	public Integer getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(Integer saleRate) {
		this.saleRate = saleRate;
	}

	@Basic()
	@Column(name = "expRate", precision = 10)
	public Integer getExpRate() {
		return expRate;
	}

	public void setExpRate(Integer expRate) {
		this.expRate = expRate;
	}

	@Basic()
	@Column(name = "strongRate", precision = 10)
	public Integer getStrongRate() {
		return strongRate;
	}

	public void setStrongRate(Integer strongRate) {
		this.strongRate = strongRate;
	}

	@Basic()
	@Column(name = "bossRate", precision = 10)
	public Integer getBossRate() {
		return bossRate;
	}

	public void setBossRate(Integer bossRate) {
		this.bossRate = bossRate;
	}
}