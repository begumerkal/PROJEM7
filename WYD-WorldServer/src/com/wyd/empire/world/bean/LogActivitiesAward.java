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
 * LogActivitiesAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "log_activities_award")
public class LogActivitiesAward implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer playerId;
	private Integer playerBillId;
	private String areaId;
	private String activityName;
	private Date startTime;
	private Date endTime;
	private String isSend;
	private String itemsAward;
	private String itemsAwardRemark;
	private Date createTime;
	private Date sendTime;
	private String mailTitle;
	private String mailContent;

	// Constructors
	/** default constructor */
	public LogActivitiesAward() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "laa_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "player_id")
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "is_send", length = 1)
	public String getIsSend() {
		return this.isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	@Column(name = "items_award")
	public String getItemsAward() {
		return this.itemsAward;
	}

	public void setItemsAward(String itemsAward) {
		this.itemsAward = itemsAward;
	}

	@Column(name = "items_award_remark", length = 500)
	public String getItemsAwardRemark() {
		return this.itemsAwardRemark;
	}

	public void setItemsAwardRemark(String itemsAwardRemark) {
		this.itemsAwardRemark = itemsAwardRemark;
	}

	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "send_time", length = 19)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "mail_title", length = 200)
	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	@Column(name = "mail_content", length = 500)
	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Column(name = "activity_name", length = 100)
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "player_bill_id")
	public Integer getPlayerBillId() {
		return playerBillId;
	}

	public void setPlayerBillId(Integer playerBillId) {
		this.playerBillId = playerBillId;
	}

}