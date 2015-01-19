package com.wyd.empire.world.bean;

import java.io.Serializable;

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
 * The persistent class for the tab_application database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_push")
public class Push implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String areaId; // 推送规则所属分区
	private Integer lostTime; // 离线多长时间发送（单位分钟）
	private String message; // 推送内容
	private Integer isRepeat; // 是否重复推送(0否， 1是)
	private Integer isActivation; // 是否启用（0停用，1启用）

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
	@Column(name = "areaid", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "lost_time", precision = 5)
	public Integer getLostTime() {
		return lostTime;
	}

	public void setLostTime(Integer lostTime) {
		this.lostTime = lostTime;
	}

	@Basic()
	@Column(name = "message", length = 255)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Basic()
	@Column(name = "repeat", precision = 1)
	public Integer getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Integer isRepeat) {
		this.isRepeat = isRepeat;
	}

	@Basic()
	@Column(name = "activation", precision = 1)
	public Integer getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Integer isActivation) {
		this.isActivation = isActivation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Push)) {
			return false;
		}
		Push castOther = (Push) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}