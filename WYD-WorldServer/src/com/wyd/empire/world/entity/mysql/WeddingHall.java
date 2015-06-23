package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_tips database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_wedding_hall")
public class WeddingHall implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int manId;
	private int womanId;
	private int wedtype;
	private Date startTime;
	private Date endTime;
	private int rewardNum;
	private int rewardGoldNum;
	private int areaId;
	private int avgGoldNum; // 平均每个红包最少的金额数
	private int otherGoldNum; // 机动分配金额数

	public WeddingHall() {
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
	@Column(name = "manId", precision = 10)
	public int getManId() {
		return manId;
	}

	public void setManId(int manId) {
		this.manId = manId;
	}

	@Basic()
	@Column(name = "womanId", precision = 10)
	public int getWomanId() {
		return womanId;
	}

	public void setWomanId(int womanId) {
		this.womanId = womanId;
	}

	@Basic()
	@Column(name = "wedtype", precision = 4)
	public int getWedtype() {
		return wedtype;
	}

	public void setWedtype(int wedtype) {
		this.wedtype = wedtype;
	}

	@Basic()
	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Basic()
	@Column(name = "endTime")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	@Basic()
	@Column(name = "rewardNum", precision = 10)
	public int getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}

	@Basic()
	@Column(name = "rewardGoldNum", precision = 10)
	public int getRewardGoldNum() {
		return rewardGoldNum;
	}

	public void setRewardGoldNum(int rewardGoldNum) {
		this.rewardGoldNum = rewardGoldNum;
	}

	@Basic()
	@Column(name = "areaId", precision = 4)
	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "avgGoldNum", precision = 10)
	public int getAvgGoldNum() {
		return avgGoldNum;
	}

	public void setAvgGoldNum(int avgGoldNum) {
		this.avgGoldNum = avgGoldNum;
	}

	@Basic()
	@Column(name = "otherGoldNum", precision = 10)
	public int getOtherGoldNum() {
		return otherGoldNum;
	}

	public void setOtherGoldNum(int otherGoldNum) {
		this.otherGoldNum = otherGoldNum;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WeddingHall)) {
			return false;
		}
		WeddingHall castOther = (WeddingHall) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}