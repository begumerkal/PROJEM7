package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "tab_bossmap_reward")
public class BossmapReward implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 奖励序号
	private Integer bossMapId; // 副本ID
	private Integer rewardType; // 副本奖励物品类型：1 一级难度奖励，2 二级难度奖励， 3 三级难度奖励，4 钻石专属奖励
	private Integer shopItemId; // 物品ID
	private String name; // 物品名字
	private String icon; // 物品图标
	private Integer days; // 物品奖励时间，-1表示没有
	private Integer count; // 物品奖励个数，-1表示没有
	private Integer sex; // 该奖励是否存在性别区分：0、男；1、女；2、不分性别
	private Integer probabilityId; // 权值id

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
	@Column(name = "bossMap_id", precision = 10)
	public Integer getBossMapId() {
		return bossMapId;
	}

	public void setBossMapId(Integer bossMapId) {
		this.bossMapId = bossMapId;
	}

	/**
	 * 副本奖励物品类型：1 一级难度奖励，2 二级难度奖励， 3 三级难度奖励，4 钻石专属奖励
	 * 
	 * @return
	 */
	@Basic()
	@Column(name = "reward_type", precision = 10)
	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	@Basic()
	@Column(name = "shopItem_id", precision = 10)
	public Integer getShopItemId() {
		return shopItemId;
	}

	public void setShopItemId(Integer shopItemId) {
		this.shopItemId = shopItemId;
	}

	@Basic()
	@Column(name = "name", length = 16)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "icon", length = 255)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "days", precision = 10)
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Basic()
	@Column(name = "count", precision = 10)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Basic()
	@Column(name = "sex", precision = 2)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Basic()
	@Column(name = "probability_id", precision = 10)
	public Integer getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Integer probabilityId) {
		this.probabilityId = probabilityId;
	}
}