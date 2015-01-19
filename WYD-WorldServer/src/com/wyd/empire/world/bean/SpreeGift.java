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
 * The persistent class for the tab_spreegift database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_spreegift")
public class SpreeGift implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 序号
	private Integer shopItemId; // 礼包物品ID
	private ShopItem shopItem1; // 男生获得物品
	private ShopItem shopItem2; // 女生获得物品
	private Integer days; // 物品奖励时间，-1表示没有
	private Integer count; // 物品奖励个数，-1表示没有
	private Integer start_chance; // 奖励开始概率，万份比
	private Integer end_chance; // 奖励结束概率，万份比
	private Integer chance; // 当前物品的概率比值，万份比

	public SpreeGift() {
		shopItem1 = new ShopItem();
		shopItem2 = new ShopItem();
	}

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
	@Column(name = "shopItemId", precision = 10)
	public Integer getShopItemId() {
		return shopItemId;
	}

	public void setShopItemId(Integer shopItemId) {
		this.shopItemId = shopItemId;
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
	@Column(name = "start_chance", precision = 10)
	public Integer getStart_chance() {
		return start_chance;
	}

	public void setStart_chance(Integer startChance) {
		start_chance = startChance;
	}

	@Basic()
	@Column(name = "end_chance", precision = 10)
	public Integer getEnd_chance() {
		return end_chance;
	}

	public void setEnd_chance(Integer endChance) {
		end_chance = endChance;
	}

	@Basic()
	@Column(name = "chance", precision = 10)
	public Integer getChance() {
		return chance;
	}

	public void setChance(Integer chance) {
		this.chance = chance;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId1", referencedColumnName = "id")
	public ShopItem getShopItem1() {
		return shopItem1;
	}

	public void setShopItem1(ShopItem shopItem1) {
		this.shopItem1 = shopItem1;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId2", referencedColumnName = "id")
	public ShopItem getShopItem2() {
		return shopItem2;
	}

	public void setShopItem2(ShopItem shopItem2) {
		this.shopItem2 = shopItem2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpreeGift)) {
			return false;
		}
		SpreeGift castOther = (SpreeGift) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}