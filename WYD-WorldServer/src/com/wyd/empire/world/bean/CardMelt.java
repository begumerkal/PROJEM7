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
 * 卡牌熔炼配置表
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_cardmelt")
public class CardMelt implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int shopItemId; // 卡牌ID
	private int meltNum; // 熔炼后得到的碎片数量

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
	@Column(name = "melt_num")
	public int getMeltNum() {
		return meltNum;
	}

	public void setMeltNum(int meltNum) {
		this.meltNum = meltNum;
	}

}