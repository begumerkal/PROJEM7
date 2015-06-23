package com.wyd.empire.world.entity.mysql;

// default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TabActivitiesAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_month_card")
public class MonthCard implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private int cardId; // 月卡id
	private String monthCardName; // 月卡名
	private int purchaseAmount; // 月卡购买所需金额
	private int dailyRebate; // 每日返还钻石数

	// Constructors
	/** default constructor */
	public MonthCard() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "m_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "card_id")
	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	@Column(name = "month_card_name", length = 50)
	public String getMonthCardName() {
		return monthCardName;
	}

	public void setMonthCardName(String monthCardName) {
		this.monthCardName = monthCardName;
	}

	@Column(name = "purchase_amount")
	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Column(name = "daily_rebate")
	public int getDailyRebate() {
		return dailyRebate;
	}

	public void setDailyRebate(int dailyRebate) {
		this.dailyRebate = dailyRebate;
	}

}