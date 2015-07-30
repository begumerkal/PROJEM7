package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseOnlineWorship entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_online_worship", catalog = "game3")
public class BaseOnlineWorship implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short week;
	private Integer monsterId;
	private String award1;
	private String award2;
	private String award3;
	private String award4;
	private String award5;
	private Integer copy;

	// Constructors

	/** default constructor */
	public BaseOnlineWorship() {
	}

	/** full constructor */
	public BaseOnlineWorship(Integer id, Short week, Integer monsterId,
			String award1, String award2, String award3, String award4,
			String award5, Integer copy) {
		this.id = id;
		this.week = week;
		this.monsterId = monsterId;
		this.award1 = award1;
		this.award2 = award2;
		this.award3 = award3;
		this.award4 = award4;
		this.award5 = award5;
		this.copy = copy;
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

	@Column(name = "week", nullable = false)
	public Short getWeek() {
		return this.week;
	}

	public void setWeek(Short week) {
		this.week = week;
	}

	@Column(name = "monster_id", nullable = false)
	public Integer getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	@Column(name = "award_1", nullable = false, length = 500)
	public String getAward1() {
		return this.award1;
	}

	public void setAward1(String award1) {
		this.award1 = award1;
	}

	@Column(name = "award_2", nullable = false, length = 500)
	public String getAward2() {
		return this.award2;
	}

	public void setAward2(String award2) {
		this.award2 = award2;
	}

	@Column(name = "award_3", nullable = false, length = 500)
	public String getAward3() {
		return this.award3;
	}

	public void setAward3(String award3) {
		this.award3 = award3;
	}

	@Column(name = "award_4", nullable = false, length = 500)
	public String getAward4() {
		return this.award4;
	}

	public void setAward4(String award4) {
		this.award4 = award4;
	}

	@Column(name = "award_5", nullable = false, length = 65535)
	public String getAward5() {
		return this.award5;
	}

	public void setAward5(String award5) {
		this.award5 = award5;
	}

	@Column(name = "Copy", nullable = false)
	public Integer getCopy() {
		return this.copy;
	}

	public void setCopy(Integer copy) {
		this.copy = copy;
	}

}