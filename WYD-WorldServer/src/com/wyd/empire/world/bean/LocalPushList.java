package com.wyd.empire.world.bean;

// default package
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wyd.empire.world.common.util.DateUtil;

/**
 * TabActivitiesAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_local_push_list")
public class LocalPushList implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String title; // 标题
	private String content; // 内容
	private Date startTime; // 开始时间
	private Date endTime; // 结束时间
	private Date startPushTime; // 开始提交推送时间
	private String daysOfWeek; // 每周重复 2,3
	private Integer type; // 定时类型: type 1.每天 2.指定日期3.离线某时间推
	private Integer version; // 版本号
	private Integer offlineHour; // 离线小时

	// Constructors
	/** default constructor */
	public LocalPushList() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "l_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 200)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Column(name = "start_push_time", length = 19)
	public Date getStartPushTime() {
		return startPushTime;
	}

	public void setStartPushTime(Date startPushTime) {
		this.startPushTime = startPushTime;
	}

	@Column(name = "days_of_week", length = 50)
	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	@Column(name = "type", length = 2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "offline_hour")
	public Integer getOfflineHour() {
		return offlineHour;
	}

	public void setOfflineHour(Integer offlineHour) {
		this.offlineHour = offlineHour;
	}

	@Transient
	public String getStartTimeByStr() {
		return startTime == null ? DateUtil.format(new Date()) : DateUtil.format(startTime);
	}

	@Transient
	public String getEndTimeByStr() {
		return endTime == null ? DateUtil.format(new Date()) : DateUtil.format(endTime);
	}

	@Transient
	public String getStartPushTimeByStr() {
		return startPushTime == null ? DateUtil.formatTime(new Date()) : DateUtil.formatTime(startPushTime);
	}

}