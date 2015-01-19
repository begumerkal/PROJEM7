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
 * @author zguoqiu
 */
@Entity()
@Table(name = "tab_invite_reward")
public class InviteReward implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String areaId; // 服务器id
	private int rewardGrade; // 奖励级别
	private int inviteNum; // 需要邀请的玩家数量
	private String rewardItemId; // 玩家奖励物品id（id|sex，id|sex）
	private String rewardItemName; // 玩家奖励物品名称
	private String rewardItemIcon; // 玩家奖励物品图标
	private String rewardItemNum; // 玩家奖励物品数量（num|type，num|type）type1数量，-1天数
	private String rewardItemLevel; // 玩家奖励物品强化等级
	private String rewardItemRemark; // 玩家奖励物品描述

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
	@Column(name = "reward_grade", precision = 3)
	public int getRewardGrade() {
		return rewardGrade;
	}

	public void setRewardGrade(int rewardGrade) {
		this.rewardGrade = rewardGrade;
	}

	@Basic()
	@Column(name = "invite_num", precision = 3)
	public int getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(int inviteNum) {
		this.inviteNum = inviteNum;
	}

	@Basic()
	@Column(name = "reward_item_id", length = 1000)
	public String getRewardItemId() {
		return rewardItemId;
	}

	public void setRewardItemId(String rewardItemId) {
		this.rewardItemId = rewardItemId;
	}

	@Basic()
	@Column(name = "reward_item_name", length = 1000)
	public String getRewardItemName() {
		return rewardItemName;
	}

	public void setRewardItemName(String rewardItemName) {
		this.rewardItemName = rewardItemName;
	}

	@Basic()
	@Column(name = "reward_item_icon", length = 1000)
	public String getRewardItemIcon() {
		return rewardItemIcon;
	}

	public void setRewardItemIcon(String rewardItemIcon) {
		this.rewardItemIcon = rewardItemIcon;
	}

	@Basic()
	@Column(name = "reward_item_num", length = 1000)
	public String getRewardItemNum() {
		return rewardItemNum;
	}

	public void setRewardItemNum(String rewardItemNum) {
		this.rewardItemNum = rewardItemNum;
	}

	@Basic()
	@Column(name = "reward_item_level", length = 1000)
	public String getRewardItemLevel() {
		return rewardItemLevel;
	}

	public void setRewardItemLevel(String rewardItemLevel) {
		this.rewardItemLevel = rewardItemLevel;
	}

	@Basic()
	@Column(name = "reward_item_remark", length = 1000)
	public String getRewardItemRemark() {
		return rewardItemRemark;
	}

	public void setRewardItemRemark(String rewardItemRemark) {
		this.rewardItemRemark = rewardItemRemark;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InviteReward)) {
			return false;
		}
		InviteReward castOther = (InviteReward) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}