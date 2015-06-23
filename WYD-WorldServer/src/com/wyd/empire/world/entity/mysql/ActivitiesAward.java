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
 * TabActivitiesAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_activities_award")
public class ActivitiesAward implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String areaId;
	private String activityName;
	private Date startTime;
	private Date endTime;
	private String itemFormula;
	private String itemRemark;
	private Integer finishType;
	private String finishCondition;
	private String mailTitle;
	private String mailContent;
	private String isSend;
	private String playerIds;

	// Constructors
	/** default constructor */
	public ActivitiesAward() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "aa_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "activity_name", length = 100)
	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "item_formula", length = 500)
	public String getItemFormula() {
		return this.itemFormula;
	}

	public void setItemFormula(String itemFormula) {
		this.itemFormula = itemFormula;
	}

	@Column(name = "finish_type")
	public Integer getFinishType() {
		return this.finishType;
	}

	public void setFinishType(Integer finishType) {
		this.finishType = finishType;
	}

	@Column(name = "finish_condition", length = 500)
	public String getFinishCondition() {
		return this.finishCondition;
	}

	public void setFinishCondition(String finishCondition) {
		this.finishCondition = finishCondition;
	}

	@Column(name = "mail_title", length = 200)
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

	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "item_remark", length = 10)
	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}

	@Column(name = "is_send", length = 1)
	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	@Column(name = "player_ids")
	public String getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(String playerIds) {
		this.playerIds = playerIds;
	}
}