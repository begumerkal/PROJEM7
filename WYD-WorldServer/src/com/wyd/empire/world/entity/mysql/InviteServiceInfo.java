package com.wyd.empire.world.entity.mysql;

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
@Table(name = "tab_service_info")
public class InviteServiceInfo implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String areaId; // 服务器id
	private String serviceName; // 服务器名称
	private int inviteLevel; // 成功邀请等级
	private String rewardRemark; // 邀请奖励描述

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
	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "service_name", length = 10)
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Basic()
	@Column(name = "invite_level", precision = 2)
	public int getInviteLevel() {
		return inviteLevel;
	}

	public void setInviteLevel(int inviteLevel) {
		this.inviteLevel = inviteLevel;
	}

	@Basic()
	@Column(name = "reward_remark", length = 500)
	public String getRewardRemark() {
		return rewardRemark;
	}

	public void setRewardRemark(String rewardRemark) {
		this.rewardRemark = rewardRemark;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InviteServiceInfo)) {
			return false;
		}
		InviteServiceInfo castOther = (InviteServiceInfo) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}