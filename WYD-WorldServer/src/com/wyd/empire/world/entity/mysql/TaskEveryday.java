package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the log_taskeveryday database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_taskeveryday")
public class TaskEveryday implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer finishTaskNum;
	private Integer getTaskNum;
	private Date staTime;
	private Task task;

	public TaskEveryday() {
		task = new Task();
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "finishTaskNum", precision = 10)
	public Integer getFinishTaskNum() {
		return this.finishTaskNum;
	}

	public void setFinishTaskNum(Integer finishTaskNum) {
		this.finishTaskNum = finishTaskNum;
	}

	@Basic()
	@Column(name = "getTaskNum", precision = 10)
	public Integer getGetTaskNum() {
		return this.getTaskNum;
	}

	public void setGetTaskNum(Integer getTaskNum) {
		this.getTaskNum = getTaskNum;
	}

	@Basic()
	@Column(name = "staTime")
	public Date getStaTime() {
		return this.staTime;
	}

	public void setStaTime(Date staTime) {
		this.staTime = staTime;
	}

	// bi-directional many-to-one association to Task
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", referencedColumnName = "id")
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskEveryday)) {
			return false;
		}
		TaskEveryday castOther = (TaskEveryday) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}