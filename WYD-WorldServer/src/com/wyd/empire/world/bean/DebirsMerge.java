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
 * 卡牌碎片合成卡牌配置表
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_debirsmerge")
public class DebirsMerge implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int level; // 碎牌品质
	private int shopItemId; // 合成的卡牌ID
	private int rate; // 得到概率

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
	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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
	@Column(name = "rate")
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}