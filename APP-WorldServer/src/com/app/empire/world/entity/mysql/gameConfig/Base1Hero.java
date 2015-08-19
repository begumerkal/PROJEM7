package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1Hero entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_hero", catalog = "game3")
public class Base1Hero implements java.io.Serializable {

	// Fields

	private Integer id;
	private String picId;
	private String modelId;
	private Integer isCityshow;
	private Boolean ingroup;
	private Integer proType;
	private Integer attackType;
	private Integer raceType;
	private Integer flagType;
	private Integer positionType;
	private String name;
	private Integer sex;
	private String introduce;
	private String info;
	private Integer star;
	private String culture;
	private Integer needSton;
	private Integer heroExtId;
	private Integer isShow;
	private String showPosition;
	private Integer isOpen;

	// Constructors

	/** default constructor */
	public Base1Hero() {
	}

	/** minimal constructor */
	public Base1Hero(Integer id, String picId, String modelId,
			Integer isCityshow, Boolean ingroup, Integer proType,
			Integer attackType, Integer raceType, Integer flagType,
			Integer positionType, String name, Integer sex, String introduce,
			String info, Integer star, String culture, Integer heroExtId,
			Integer isShow, String showPosition, Integer isOpen) {
		this.id = id;
		this.picId = picId;
		this.modelId = modelId;
		this.isCityshow = isCityshow;
		this.ingroup = ingroup;
		this.proType = proType;
		this.attackType = attackType;
		this.raceType = raceType;
		this.flagType = flagType;
		this.positionType = positionType;
		this.name = name;
		this.sex = sex;
		this.introduce = introduce;
		this.info = info;
		this.star = star;
		this.culture = culture;
		this.heroExtId = heroExtId;
		this.isShow = isShow;
		this.showPosition = showPosition;
		this.isOpen = isOpen;
	}

	/** full constructor */
	public Base1Hero(Integer id, String picId, String modelId,
			Integer isCityshow, Boolean ingroup, Integer proType,
			Integer attackType, Integer raceType, Integer flagType,
			Integer positionType, String name, Integer sex, String introduce,
			String info, Integer star, String culture, Integer needSton,
			Integer heroExtId, Integer isShow, String showPosition,
			Integer isOpen) {
		this.id = id;
		this.picId = picId;
		this.modelId = modelId;
		this.isCityshow = isCityshow;
		this.ingroup = ingroup;
		this.proType = proType;
		this.attackType = attackType;
		this.raceType = raceType;
		this.flagType = flagType;
		this.positionType = positionType;
		this.name = name;
		this.sex = sex;
		this.introduce = introduce;
		this.info = info;
		this.star = star;
		this.culture = culture;
		this.needSton = needSton;
		this.heroExtId = heroExtId;
		this.isShow = isShow;
		this.showPosition = showPosition;
		this.isOpen = isOpen;
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

	@Column(name = "pic_id", nullable = false, length = 11)
	public String getPicId() {
		return this.picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	@Column(name = "model_id", nullable = false, length = 11)
	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Column(name = "is_cityshow", nullable = false)
	public Integer getIsCityshow() {
		return this.isCityshow;
	}

	public void setIsCityshow(Integer isCityshow) {
		this.isCityshow = isCityshow;
	}

	@Column(name = "ingroup", nullable = false)
	public Boolean getIngroup() {
		return this.ingroup;
	}

	public void setIngroup(Boolean ingroup) {
		this.ingroup = ingroup;
	}

	@Column(name = "pro_type", nullable = false)
	public Integer getProType() {
		return this.proType;
	}

	public void setProType(Integer proType) {
		this.proType = proType;
	}

	@Column(name = "attack_type", nullable = false)
	public Integer getAttackType() {
		return this.attackType;
	}

	public void setAttackType(Integer attackType) {
		this.attackType = attackType;
	}

	@Column(name = "race_type", nullable = false)
	public Integer getRaceType() {
		return this.raceType;
	}

	public void setRaceType(Integer raceType) {
		this.raceType = raceType;
	}

	@Column(name = "flag_type", nullable = false)
	public Integer getFlagType() {
		return this.flagType;
	}

	public void setFlagType(Integer flagType) {
		this.flagType = flagType;
	}

	@Column(name = "position_type", nullable = false)
	public Integer getPositionType() {
		return this.positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "introduce", nullable = false, length = 256)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "info", nullable = false, length = 500)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "star", nullable = false)
	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Column(name = "culture", nullable = false, length = 200)
	public String getCulture() {
		return this.culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	@Column(name = "need_ston")
	public Integer getNeedSton() {
		return this.needSton;
	}

	public void setNeedSton(Integer needSton) {
		this.needSton = needSton;
	}

	@Column(name = "hero_ext_id", nullable = false)
	public Integer getHeroExtId() {
		return this.heroExtId;
	}

	public void setHeroExtId(Integer heroExtId) {
		this.heroExtId = heroExtId;
	}

	@Column(name = "is_show", nullable = false)
	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "show_position", nullable = false, length = 11)
	public String getShowPosition() {
		return this.showPosition;
	}

	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}

	@Column(name = "is_open", nullable = false)
	public Integer getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

}