package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_active_reward database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_active_reward")
public class ActiveReward implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;// 活跃度奖励id
	private int activityDemand;// 活跃度要求值
	private String reward;// 奖励物品

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
	@Column(name = "activity_demand", precision = 10)
	public int getActivityDemand() {
		return activityDemand;
	}

	public void setActivityDemand(int activityDemand) {
		this.activityDemand = activityDemand;
	}

	@Basic()
	@Column(name = "reward", length = 255)
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
}