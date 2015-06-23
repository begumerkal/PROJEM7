package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_rewarditems database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_reward")
public class Reward implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int param;
	private int type;// 0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励，6在线奖励，7在线抽奖奖励
	private String reward;
	private String rewardParam;// 奖励附加信息

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
	@Column(name = "param", precision = 10)
	public int getParam() {
		return param;
	}

	public void setParam(int param) {
		this.param = param;
	}

	@Basic()
	@Column(name = "type", precision = 10)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "reward", length = 255)
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	@Basic()
	@Column(name = "reward_param", length = 255)
	public String getRewardParam() {
		return rewardParam;
	}

	public void setRewardParam(String rewardParam) {
		this.rewardParam = rewardParam;
	}
}