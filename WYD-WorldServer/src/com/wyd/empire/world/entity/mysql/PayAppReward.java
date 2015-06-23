package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

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
 * 付费包奖励.
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_payapp_reward")
public class PayAppReward implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ShopItem shopItem;
	private int days;
	private int count;
	private int strongLevel;
	private String mailTitle;// 邮件标题
	private String mailContent;// 邮件内容

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
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

	@Column(name = "strong_level")
	public Integer getStrongLevel() {
		return this.strongLevel;
	}

	public void setStrongLevel(Integer strongLevel) {
		this.strongLevel = strongLevel;
	}

	@Column(name = "mail_title")
	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	@Column(name = "mail_content")
	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

}
