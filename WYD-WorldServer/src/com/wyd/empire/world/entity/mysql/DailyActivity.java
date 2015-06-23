package com.wyd.empire.world.entity.mysql;

// default package
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * dailyActivity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_daily_activities")
public class DailyActivity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer daysOfWeek;
	private String description;
	private String awardCondition;
	private String rewardsAdd;
	private Integer rewardRateAdd;
	private String rewardsSub;
	private Integer rewardRateSub;
	private String rewardItems;
	private String mailTitle;
	private String mailContent;
	private String areaId;
	private Integer status;

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 12)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "startTime", length = 0)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 0)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "daysOfWeek", length = 15)
	public Integer getDaysOfWeek() {
		return this.daysOfWeek;
	}

	public void setDaysOfWeek(Integer daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "awardCondition", length = 50)
	public String getAwardCondition() {
		return this.awardCondition;
	}

	public void setAwardCondition(String awardCondition) {
		this.awardCondition = awardCondition;
	}

	@Column(name = "rewardsAdd", length = 50)
	public String getRewardsAdd() {
		return rewardsAdd;
	}

	public void setRewardsAdd(String rewardsAdd) {
		this.rewardsAdd = rewardsAdd;
	}

	@Column(name = "rewardRateAdd", length = 50)
	public Integer getRewardRateAdd() {
		return rewardRateAdd;
	}

	public void setRewardRateAdd(Integer rewardRateAdd) {
		this.rewardRateAdd = rewardRateAdd;
	}

	@Column(name = "rewardsSub", length = 50)
	public String getRewardsSub() {
		return rewardsSub;
	}

	public void setRewardsSub(String rewardsSub) {
		this.rewardsSub = rewardsSub;
	}

	@Column(name = "rewardRateSub", length = 50)
	public Integer getRewardRateSub() {
		return rewardRateSub;
	}

	public void setRewardRateSub(Integer rewardRateSub) {
		this.rewardRateSub = rewardRateSub;
	}

	@Column(name = "rewardItems", length = 100)
	public String getRewardItems() {
		return this.rewardItems;
	}

	public void setRewardItems(String rewardItems) {
		this.rewardItems = rewardItems;
	}

	@Column(name = "mailTitle", length = 50)
	public String getMailTitle() {
		return this.mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	@Column(name = "mailContent", length = 200)
	public String getMailContent() {
		return this.mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Column(name = "areaId", length = 10)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}