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
 * 卡牌兑换
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_cardexchange")
public class CardExchange implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int shopItemId; // 可兑换的卡牌ID
	private int showRate; // 出现的概率
	private int number; // 每人兑换次数
	private int price; // 价格（熔炼碎片)

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
	@Column(name = "shopItemId")
	public int getShopItemId() {
		return shopItemId;
	}

	public void setShopItemId(int shopItemId) {
		this.shopItemId = shopItemId;
	}

	@Basic()
	@Column(name = "showRate")
	public int getShowRate() {
		return showRate;
	}

	public void setShowRate(int showRate) {
		this.showRate = showRate;
	}

	@Basic()
	@Column(name = "number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Basic()
	@Column(name = "price")
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}