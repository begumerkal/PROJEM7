package com.wyd.empire.world.bean;

// default package
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TabFullServiceReward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_full_service_reward")
public class FullServiceReward implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String areaId;
	private Integer minLevel;
	private Integer maxLevel;
	private String itemReward;
	private String itemRewardRemark;
	private String mailTitle;
	private String mailContent;
	private String isAll;
	private Integer currentCount;
	private Date startTime;
	private Date endTime;

	// Constructors
	/** default constructor */
	public FullServiceReward() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fr_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "min_level")
	public Integer getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}

	@Column(name = "max_level")
	public Integer getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}

	@Column(name = "item_reward", length = 500)
	public String getItemReward() {
		return this.itemReward;
	}

	public void setItemReward(String itemReward) {
		this.itemReward = itemReward;
	}

	@Column(name = "item_reward_remark", length = 500)
	public String getItemRewardRemark() {
		return this.itemRewardRemark;
	}

	public void setItemRewardRemark(String itemRewardRemark) {
		this.itemRewardRemark = itemRewardRemark;
	}

	@Column(name = "mail_title")
	public String getMailTitle() {
		return this.mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	@Column(name = "mail_content", length = 500)
	public String getMailContent() {
		return this.mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Column(name = "is_all", length = 19)
	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	@Column(name = "current_count", length = 19)
	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}