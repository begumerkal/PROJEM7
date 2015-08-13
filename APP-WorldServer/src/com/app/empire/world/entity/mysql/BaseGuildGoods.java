package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseGuildGoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_guild_goods", catalog = "game3")
public class BaseGuildGoods implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer storeLv;
	private Integer goodsExtId;
	private Integer num;
	private Integer activity;
	private Integer guildGold;

	// Constructors

	/** default constructor */
	public BaseGuildGoods() {
	}

	/** full constructor */
	public BaseGuildGoods(Integer id, Integer storeLv, Integer goodsExtId,
			Integer num, Integer activity, Integer guildGold) {
		this.id = id;
		this.storeLv = storeLv;
		this.goodsExtId = goodsExtId;
		this.num = num;
		this.activity = activity;
		this.guildGold = guildGold;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "store_lv", nullable = false)
	public Integer getStoreLv() {
		return this.storeLv;
	}

	public void setStoreLv(Integer storeLv) {
		this.storeLv = storeLv;
	}

	@Column(name = "goods_ext_id", nullable = false)
	public Integer getGoodsExtId() {
		return this.goodsExtId;
	}

	public void setGoodsExtId(Integer goodsExtId) {
		this.goodsExtId = goodsExtId;
	}

	@Column(name = "num", nullable = false)
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "activity", nullable = false)
	public Integer getActivity() {
		return this.activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	@Column(name = "guild_gold", nullable = false)
	public Integer getGuildGold() {
		return this.guildGold;
	}

	public void setGuildGold(Integer guildGold) {
		this.guildGold = guildGold;
	}

}