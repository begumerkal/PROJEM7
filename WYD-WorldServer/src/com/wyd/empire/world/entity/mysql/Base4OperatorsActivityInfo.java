package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base4OperatorsActivityInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base4_operators_activity_info", catalog = "game3")
public class Base4OperatorsActivityInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer oid;
	private Integer condition;
	private String conditionInfo;
	private String awardGoodsExtId;
	private String awardRes;

	// Constructors

	/** default constructor */
	public Base4OperatorsActivityInfo() {
	}

	/** full constructor */
	public Base4OperatorsActivityInfo(Integer id, Integer oid,
			Integer condition, String conditionInfo, String awardGoodsExtId,
			String awardRes) {
		this.id = id;
		this.oid = oid;
		this.condition = condition;
		this.conditionInfo = conditionInfo;
		this.awardGoodsExtId = awardGoodsExtId;
		this.awardRes = awardRes;
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

	@Column(name = "oid", nullable = false)
	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	@Column(name = "condition", nullable = false)
	public Integer getCondition() {
		return this.condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	@Column(name = "condition_info", nullable = false, length = 20)
	public String getConditionInfo() {
		return this.conditionInfo;
	}

	public void setConditionInfo(String conditionInfo) {
		this.conditionInfo = conditionInfo;
	}

	@Column(name = "award_goods_ext_id", nullable = false, length = 200)
	public String getAwardGoodsExtId() {
		return this.awardGoodsExtId;
	}

	public void setAwardGoodsExtId(String awardGoodsExtId) {
		this.awardGoodsExtId = awardGoodsExtId;
	}

	@Column(name = "award_res", nullable = false, length = 200)
	public String getAwardRes() {
		return this.awardRes;
	}

	public void setAwardRes(String awardRes) {
		this.awardRes = awardRes;
	}

}