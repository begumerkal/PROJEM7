package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base4OperatorsActivity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base4_operators_activity", catalog = "game3")
public class Base4OperatorsActivity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sys;
	private Integer conditionType;
	private Integer cycle;
	private Integer activityTime1;
	private Integer activityTime2;
	private Integer userLv;
	private Integer awardTime1;
	private Integer awardTime2;
	private Integer status;
	private String info;

	// Constructors

	/** default constructor */
	public Base4OperatorsActivity() {
	}

	/** full constructor */
	public Base4OperatorsActivity(Integer id, Integer sys,
			Integer conditionType, Integer cycle, Integer activityTime1,
			Integer activityTime2, Integer userLv, Integer awardTime1,
			Integer awardTime2, Integer status, String info) {
		this.id = id;
		this.sys = sys;
		this.conditionType = conditionType;
		this.cycle = cycle;
		this.activityTime1 = activityTime1;
		this.activityTime2 = activityTime2;
		this.userLv = userLv;
		this.awardTime1 = awardTime1;
		this.awardTime2 = awardTime2;
		this.status = status;
		this.info = info;
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

	@Column(name = "sys", nullable = false)
	public Integer getSys() {
		return this.sys;
	}

	public void setSys(Integer sys) {
		this.sys = sys;
	}

	@Column(name = "condition_type", nullable = false)
	public Integer getConditionType() {
		return this.conditionType;
	}

	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}

	@Column(name = "cycle", nullable = false)
	public Integer getCycle() {
		return this.cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Column(name = "activity_time1", nullable = false)
	public Integer getActivityTime1() {
		return this.activityTime1;
	}

	public void setActivityTime1(Integer activityTime1) {
		this.activityTime1 = activityTime1;
	}

	@Column(name = "activity_time2", nullable = false)
	public Integer getActivityTime2() {
		return this.activityTime2;
	}

	public void setActivityTime2(Integer activityTime2) {
		this.activityTime2 = activityTime2;
	}

	@Column(name = "user_lv", nullable = false)
	public Integer getUserLv() {
		return this.userLv;
	}

	public void setUserLv(Integer userLv) {
		this.userLv = userLv;
	}

	@Column(name = "award_time1", nullable = false)
	public Integer getAwardTime1() {
		return this.awardTime1;
	}

	public void setAwardTime1(Integer awardTime1) {
		this.awardTime1 = awardTime1;
	}

	@Column(name = "award_time2", nullable = false)
	public Integer getAwardTime2() {
		return this.awardTime2;
	}

	public void setAwardTime2(Integer awardTime2) {
		this.awardTime2 = awardTime2;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "info", nullable = false, length = 300)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}