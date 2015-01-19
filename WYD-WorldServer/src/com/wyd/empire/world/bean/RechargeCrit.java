package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TabActivitiesAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_rechargecrit")
public class RechargeCrit implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer lowNum;
	private Integer highNum;
	private Integer addRatio;
	private Integer fullRatio;
	private Integer doubleRatio;
	private Integer num1;
	private Integer fiveRatio;
	private Integer num2;
	private Integer tenRatio;
	private Integer num3;

	// Constructors
	/** default constructor */
	public RechargeCrit() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "low_num", length = 100)
	public Integer getLowNum() {
		return lowNum;
	}

	public void setLowNum(Integer lowNum) {
		this.lowNum = lowNum;
	}

	@Column(name = "high_num", length = 100)
	public Integer getHighNum() {
		return highNum;
	}

	public void setHighNum(Integer highNum) {
		this.highNum = highNum;
	}

	@Column(name = "add_ratio", length = 100)
	public Integer getAddRatio() {
		return addRatio;
	}

	public void setAddRatio(Integer addRatio) {
		this.addRatio = addRatio;
	}

	@Column(name = "full_ratio", length = 100)
	public Integer getFullRatio() {
		return fullRatio;
	}

	public void setFullRatio(Integer fullRatio) {
		this.fullRatio = fullRatio;
	}

	@Column(name = "double_ratio", length = 100)
	public Integer getDoubleRatio() {
		return doubleRatio;
	}

	public void setDoubleRatio(Integer doubleRatio) {
		this.doubleRatio = doubleRatio;
	}

	@Column(name = "num_1", length = 100)
	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	@Column(name = "five_ratio", length = 100)
	public Integer getFiveRatio() {
		return fiveRatio;
	}

	public void setFiveRatio(Integer fiveRatio) {
		this.fiveRatio = fiveRatio;
	}

	@Column(name = "num_2", length = 100)
	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	@Column(name = "ten_ratio", length = 100)
	public Integer getTenRatio() {
		return tenRatio;
	}

	public void setTenRatio(Integer tenRatio) {
		this.tenRatio = tenRatio;
	}

	@Column(name = "num_3", length = 100)
	public Integer getNum3() {
		return num3;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}
}
