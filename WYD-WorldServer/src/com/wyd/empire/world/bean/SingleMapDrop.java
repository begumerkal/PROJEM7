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

/**
 * 单人副本掉落
 * 
 * @author zengxc
 *
 */
@Entity()
@Table(name = "tab_singlemap_drop")
public class SingleMapDrop implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer dropId;
	private ShopItem shopItem1;
	private ShopItem shopItem2;
	private int num;
	private int startChance;
	private int endChance;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId1", referencedColumnName = "id", nullable = false)
	public ShopItem getShopItem1() {
		return shopItem1;
	}

	public void setShopItem1(ShopItem shopItem1) {
		this.shopItem1 = shopItem1;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId2", referencedColumnName = "id", nullable = false)
	public ShopItem getShopItem2() {
		return shopItem2;
	}

	public void setShopItem2(ShopItem shopItem2) {
		this.shopItem2 = shopItem2;
	}

	@Basic()
	@Column(name = "start_chance", length = 10)
	public int getStartChance() {
		return startChance;
	}

	public void setStartChance(int startChance) {
		this.startChance = startChance;
	}

	@Basic()
	@Column(name = "end_chance", length = 10)
	public int getEndChance() {
		return endChance;
	}

	public void setEndChance(int endChance) {
		this.endChance = endChance;
	}

	@Basic()
	@Column(name = "drop_id", length = 2)
	public Integer getDropId() {
		return dropId;
	}

	public void setDropId(Integer dropId) {
		this.dropId = dropId;
	}

	@Basic()
	@Column(name = "num", length = 2)
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
