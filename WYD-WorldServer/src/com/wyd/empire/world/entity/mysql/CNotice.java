package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_notice", catalog = "game3")
public class CNotice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private Integer interval;
	private Integer upTime;
	private Integer time1;
	private Integer time2;
	private String info;

	// Constructors

	/** default constructor */
	public CNotice() {
	}

	/** minimal constructor */
	public CNotice(Integer id, Integer interval, Integer upTime, Integer time2,
			String info) {
		this.id = id;
		this.interval = interval;
		this.upTime = upTime;
		this.time2 = time2;
		this.info = info;
	}

	/** full constructor */
	public CNotice(Integer id, Integer type, Integer interval, Integer upTime,
			Integer time1, Integer time2, String info) {
		this.id = id;
		this.type = type;
		this.interval = interval;
		this.upTime = upTime;
		this.time1 = time1;
		this.time2 = time2;
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

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "interval", nullable = false)
	public Integer getInterval() {
		return this.interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	@Column(name = "up_time", nullable = false)
	public Integer getUpTime() {
		return this.upTime;
	}

	public void setUpTime(Integer upTime) {
		this.upTime = upTime;
	}

	@Column(name = "time1")
	public Integer getTime1() {
		return this.time1;
	}

	public void setTime1(Integer time1) {
		this.time1 = time1;
	}

	@Column(name = "time2", nullable = false)
	public Integer getTime2() {
		return this.time2;
	}

	public void setTime2(Integer time2) {
		this.time2 = time2;
	}

	@Column(name = "info", nullable = false, length = 600)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}