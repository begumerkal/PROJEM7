package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseGoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_goods", catalog = "game3")
public class BaseGoods implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer showId;
	private Integer goodsType;
	private Integer subType;
	private String name;
	private Integer quality;
	private Integer color;
	private String property;
	private Integer packShow;
	private Integer wrapNum;
	private Integer isUsed;
	private Integer isSell;
	private Integer delete;
	private String description;
	private String histroy;
	private Integer advenceType;
	private String info;

	// Constructors

	/** default constructor */
	public BaseGoods() {
	}

	/** full constructor */
	public BaseGoods(Integer id, Integer showId, Integer goodsType,
			Integer subType, String name, Integer quality, Integer color,
			String property, Integer packShow, Integer wrapNum, Integer isUsed,
			Integer isSell, Integer delete, String description, String histroy,
			Integer advenceType, String info) {
		this.id = id;
		this.showId = showId;
		this.goodsType = goodsType;
		this.subType = subType;
		this.name = name;
		this.quality = quality;
		this.color = color;
		this.property = property;
		this.packShow = packShow;
		this.wrapNum = wrapNum;
		this.isUsed = isUsed;
		this.isSell = isSell;
		this.delete = delete;
		this.description = description;
		this.histroy = histroy;
		this.advenceType = advenceType;
		this.info = info;
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

	@Column(name = "show_id", nullable = false)
	public Integer getShowId() {
		return this.showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	@Column(name = "goods_type", nullable = false)
	public Integer getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "sub_type", nullable = false)
	public Integer getSubType() {
		return this.subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "quality", nullable = false)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Column(name = "color", nullable = false)
	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	@Column(name = "property", nullable = false, length = 1024)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name = "pack_show", nullable = false)
	public Integer getPackShow() {
		return this.packShow;
	}

	public void setPackShow(Integer packShow) {
		this.packShow = packShow;
	}

	@Column(name = "wrap_num", nullable = false)
	public Integer getWrapNum() {
		return this.wrapNum;
	}

	public void setWrapNum(Integer wrapNum) {
		this.wrapNum = wrapNum;
	}

	@Column(name = "is_used", nullable = false)
	public Integer getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	@Column(name = "is_sell", nullable = false)
	public Integer getIsSell() {
		return this.isSell;
	}

	public void setIsSell(Integer isSell) {
		this.isSell = isSell;
	}

	@Column(name = "delete", nullable = false)
	public Integer getDelete() {
		return this.delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	@Column(name = "description", nullable = false, length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "histroy", nullable = false, length = 1024)
	public String getHistroy() {
		return this.histroy;
	}

	public void setHistroy(String histroy) {
		this.histroy = histroy;
	}

	@Column(name = "advence_type", nullable = false)
	public Integer getAdvenceType() {
		return this.advenceType;
	}

	public void setAdvenceType(Integer advenceType) {
		this.advenceType = advenceType;
	}

	@Column(name = "info", nullable = false, length = 1024)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}