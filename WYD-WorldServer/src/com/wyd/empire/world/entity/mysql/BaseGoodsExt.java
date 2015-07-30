package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseGoodsExt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_goods_ext", catalog = "game3")
public class BaseGoodsExt implements java.io.Serializable {

	// Fields

	private Integer goodsExtId;
	private Integer goodsBaseId;
	private Boolean binding;
	private String property;
	private Short start;
	private Integer nextStart;
	private Boolean goodsState;
	private String baseGemIds;
	private String gemIds;
	private Integer quality;
	private Integer diamond;
	private Short estate;
	private Integer time;
	private Integer gold;

	// Constructors

	/** default constructor */
	public BaseGoodsExt() {
	}

	/** minimal constructor */
	public BaseGoodsExt(Integer goodsExtId, Integer goodsBaseId,
			Boolean binding, Short start, Integer nextStart,
			Boolean goodsState, String baseGemIds, String gemIds,
			Integer quality, Integer diamond, Short estate, Integer time,
			Integer gold) {
		this.goodsExtId = goodsExtId;
		this.goodsBaseId = goodsBaseId;
		this.binding = binding;
		this.start = start;
		this.nextStart = nextStart;
		this.goodsState = goodsState;
		this.baseGemIds = baseGemIds;
		this.gemIds = gemIds;
		this.quality = quality;
		this.diamond = diamond;
		this.estate = estate;
		this.time = time;
		this.gold = gold;
	}

	/** full constructor */
	public BaseGoodsExt(Integer goodsExtId, Integer goodsBaseId,
			Boolean binding, String property, Short start, Integer nextStart,
			Boolean goodsState, String baseGemIds, String gemIds,
			Integer quality, Integer diamond, Short estate, Integer time,
			Integer gold) {
		this.goodsExtId = goodsExtId;
		this.goodsBaseId = goodsBaseId;
		this.binding = binding;
		this.property = property;
		this.start = start;
		this.nextStart = nextStart;
		this.goodsState = goodsState;
		this.baseGemIds = baseGemIds;
		this.gemIds = gemIds;
		this.quality = quality;
		this.diamond = diamond;
		this.estate = estate;
		this.time = time;
		this.gold = gold;
	}

	// Property accessors
	@Id
	@Column(name = "goods_ext_id", unique = true, nullable = false)
	public Integer getGoodsExtId() {
		return this.goodsExtId;
	}

	public void setGoodsExtId(Integer goodsExtId) {
		this.goodsExtId = goodsExtId;
	}

	@Column(name = "goods_base_id", nullable = false)
	public Integer getGoodsBaseId() {
		return this.goodsBaseId;
	}

	public void setGoodsBaseId(Integer goodsBaseId) {
		this.goodsBaseId = goodsBaseId;
	}

	@Column(name = "binding", nullable = false)
	public Boolean getBinding() {
		return this.binding;
	}

	public void setBinding(Boolean binding) {
		this.binding = binding;
	}

	@Column(name = "property", length = 1024)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name = "start", nullable = false)
	public Short getStart() {
		return this.start;
	}

	public void setStart(Short start) {
		this.start = start;
	}

	@Column(name = "next_start", nullable = false)
	public Integer getNextStart() {
		return this.nextStart;
	}

	public void setNextStart(Integer nextStart) {
		this.nextStart = nextStart;
	}

	@Column(name = "goods_state", nullable = false)
	public Boolean getGoodsState() {
		return this.goodsState;
	}

	public void setGoodsState(Boolean goodsState) {
		this.goodsState = goodsState;
	}

	@Column(name = "base_gem_ids", nullable = false, length = 50)
	public String getBaseGemIds() {
		return this.baseGemIds;
	}

	public void setBaseGemIds(String baseGemIds) {
		this.baseGemIds = baseGemIds;
	}

	@Column(name = "gem_ids", nullable = false, length = 200)
	public String getGemIds() {
		return this.gemIds;
	}

	public void setGemIds(String gemIds) {
		this.gemIds = gemIds;
	}

	@Column(name = "quality", nullable = false)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Column(name = "diamond", nullable = false)
	public Integer getDiamond() {
		return this.diamond;
	}

	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
	}

	@Column(name = "estate", nullable = false)
	public Short getEstate() {
		return this.estate;
	}

	public void setEstate(Short estate) {
		this.estate = estate;
	}

	@Column(name = "time", nullable = false)
	public Integer getTime() {
		return this.time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	@Column(name = "gold", nullable = false)
	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

}