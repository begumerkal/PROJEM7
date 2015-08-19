package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base2Monster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base2_monster", catalog = "game3")
public class Base2Monster implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer showId;
	private String name;
	private Integer sex;
	private Integer attackType;
	private Integer positioning;
	private Integer proType;
	private Integer monsterType;
	private Integer quality;
	private Integer color;
	private Integer star;
	private Integer aiId;
	private String info;
	private String proCoefficient;
	private String skill;
	private Integer lifeMax;

	// Constructors

	/** default constructor */
	public Base2Monster() {
	}

	/** full constructor */
	public Base2Monster(Integer id, Integer showId, String name, Integer sex,
			Integer attackType, Integer positioning, Integer proType,
			Integer monsterType, Integer quality, Integer color, Integer star,
			Integer aiId, String info, String proCoefficient, String skill,
			Integer lifeMax) {
		this.id = id;
		this.showId = showId;
		this.name = name;
		this.sex = sex;
		this.attackType = attackType;
		this.positioning = positioning;
		this.proType = proType;
		this.monsterType = monsterType;
		this.quality = quality;
		this.color = color;
		this.star = star;
		this.aiId = aiId;
		this.info = info;
		this.proCoefficient = proCoefficient;
		this.skill = skill;
		this.lifeMax = lifeMax;
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

	@Column(name = "name", nullable = false, length = 125)
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

	@Column(name = "attack_type", nullable = false)
	public Integer getAttackType() {
		return this.attackType;
	}

	public void setAttackType(Integer attackType) {
		this.attackType = attackType;
	}

	@Column(name = "positioning", nullable = false)
	public Integer getPositioning() {
		return this.positioning;
	}

	public void setPositioning(Integer positioning) {
		this.positioning = positioning;
	}

	@Column(name = "pro_type", nullable = false)
	public Integer getProType() {
		return this.proType;
	}

	public void setProType(Integer proType) {
		this.proType = proType;
	}

	@Column(name = "monster_type", nullable = false)
	public Integer getMonsterType() {
		return this.monsterType;
	}

	public void setMonsterType(Integer monsterType) {
		this.monsterType = monsterType;
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

	@Column(name = "star", nullable = false)
	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Column(name = "ai_id", nullable = false)
	public Integer getAiId() {
		return this.aiId;
	}

	public void setAiId(Integer aiId) {
		this.aiId = aiId;
	}

	@Column(name = "info", nullable = false, length = 512)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "pro_coefficient", nullable = false)
	public String getProCoefficient() {
		return this.proCoefficient;
	}

	public void setProCoefficient(String proCoefficient) {
		this.proCoefficient = proCoefficient;
	}

	@Column(name = "skill", nullable = false)
	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Column(name = "LifeMax", nullable = false)
	public Integer getLifeMax() {
		return this.lifeMax;
	}

	public void setLifeMax(Integer lifeMax) {
		this.lifeMax = lifeMax;
	}

}