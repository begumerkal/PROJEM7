package com.wyd.empire.world.bean;

import java.io.Serializable;

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
 * The persistent class for the tab_friend database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_draw_reward")
public class DrawReward implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer drawType;
	private Integer rewardType;
	private ShopItem shopitem;
	private ShopItem shopitem2;
	private Integer num;
	private Integer announcement;
	private Integer startRate;
	private Integer endRate;

	public DrawReward() {
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
	@Column(name = "draw_type", precision = 3)
	public Integer getDrawType() {
		return drawType;
	}

	public void setDrawType(Integer drawType) {
		this.drawType = drawType;
	}

	@Basic()
	@Column(name = "reward_type", precision = 3)
	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	// bi-directional many-to-one association to ShopItem
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId", referencedColumnName = "id", nullable = false)
	public ShopItem getShopitem() {
		return shopitem;
	}

	public void setShopitem(ShopItem shopitem) {
		this.shopitem = shopitem;
	}

	// bi-directional many-to-one association to ShopItem
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId2", referencedColumnName = "id", nullable = false)
	public ShopItem getShopitem2() {
		return shopitem2;
	}

	public void setShopitem2(ShopItem shopitem2) {
		this.shopitem2 = shopitem2;
	}

	@Basic()
	@Column(name = "num", precision = 10)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Basic()
	@Column(name = "announcement", precision = 10)
	public Integer getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Integer announcement) {
		this.announcement = announcement;
	}

	@Basic()
	@Column(name = "start_rate", precision = 10)
	public Integer getStartRate() {
		return startRate;
	}

	public void setStartRate(Integer startRate) {
		this.startRate = startRate;
	}

	@Basic()
	@Column(name = "end_rate", precision = 10)
	public Integer getEndRate() {
		return endRate;
	}

	public void setEndRate(Integer endRate) {
		this.endRate = endRate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DrawReward)) {
			return false;
		}
		DrawReward castOther = (DrawReward) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}