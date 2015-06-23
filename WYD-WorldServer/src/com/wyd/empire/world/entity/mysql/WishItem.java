package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;

/**
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_wishitem")
public class WishItem implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 格子序号
	private int type; // 0：普通格子 1：随机格子 2~5：礼盒
	private int rate; // 格子概率
	private String reward; // 奖励内容
	private String rewardRate; // 奖励内容概率

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
	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "rate")
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Basic()
	@Column(name = "reward")
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	@Basic()
	@Column(name = "reward_rate")
	public String getRewardRate() {
		return rewardRate;
	}

	public void setRewardRate(String rewardRate) {
		this.rewardRate = rewardRate;
	}

	/**
	 * 获取奖励物品（随机格子按概率取一个）
	 * 
	 * @param sex
	 * @return
	 */
	@Transient
	public RewardInfo getReward(int sex) {
		List<RewardInfo> rewards = ServiceUtils.getRewardInfo(reward, sex);
		if (type == 1) {
			// 根据rewardRate 来得到奖励
			int totalPrecent = 0;
			int[] rates = getReward_Rate();
			for (int rate : rates) {
				totalPrecent += rate;
			}
			int random = ServiceUtils.getRandomNum(1, totalPrecent + 1);
			int precent = 0, i = 0;
			for (int rate : rates) {
				precent += rate;
				if (precent >= random) {
					return rewards.get(i);
				}
				i++;
			}
		}
		return rewards.get(0);
	}

	@Transient
	public List<RewardInfo> getRewardList(int sex) {
		return ServiceUtils.getRewardInfo(reward, sex);
	}

	@Transient
	private int[] getReward_Rate() {
		String rate_str = rewardRate.replace("[", "").replace("]", "");
		String[] rateSplit = rate_str.split(",");
		int[] rate = new int[rateSplit.length];
		int i = 0;
		for (String rs : rateSplit) {
			rate[i] = Integer.parseInt(rs);
			i++;
		}
		return rate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WishItem)) {
			return false;
		}
		WishItem castOther = (WishItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}