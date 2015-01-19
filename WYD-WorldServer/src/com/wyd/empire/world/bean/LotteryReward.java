package com.wyd.empire.world.bean;

// default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TabLotteryReward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_lottery_reward")
public class LotteryReward implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private ShopItem shopItem;
	private Integer days;
	private Integer count;
	private Integer startChance;
	private Integer endChance;
	private Integer chance;
	private Integer strongLevel;

	// Constructors
	/** default constructor */
	public LotteryReward() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lr_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_item_id", referencedColumnName = "id")
	public ShopItem getShopItem() {
		return this.shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	@Column(name = "days")
	public Integer getDays() {
		return this.days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "start_chance")
	public Integer getStartChance() {
		return this.startChance;
	}

	public void setStartChance(Integer startChance) {
		this.startChance = startChance;
	}

	@Column(name = "end_chance")
	public Integer getEndChance() {
		return this.endChance;
	}

	public void setEndChance(Integer endChance) {
		this.endChance = endChance;
	}

	@Column(name = "chance")
	public Integer getChance() {
		return this.chance;
	}

	public void setChance(Integer chance) {
		this.chance = chance;
	}

	@Column(name = "strong_level")
	public Integer getStrongLevel() {
		return strongLevel;
	}

	public void setStrongLevel(Integer strongLevel) {
		this.strongLevel = strongLevel;
	}

}