package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3SkillExt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_skill_ext", catalog = "game3")
public class Base3SkillExt implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer skillBaseId;
	private Integer advancedLv;
	private Double damageCoefficient;
	private Double addDamage;
	private Double damage;
	private Integer nextSkillExtId;
	private String info;

	// Constructors

	/** default constructor */
	public Base3SkillExt() {
	}

	/** full constructor */
	public Base3SkillExt(Integer id, Integer skillBaseId, Integer advancedLv,
			Double damageCoefficient, Double addDamage, Double damage,
			Integer nextSkillExtId, String info) {
		this.id = id;
		this.skillBaseId = skillBaseId;
		this.advancedLv = advancedLv;
		this.damageCoefficient = damageCoefficient;
		this.addDamage = addDamage;
		this.damage = damage;
		this.nextSkillExtId = nextSkillExtId;
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

	@Column(name = "skill_base_id", nullable = false)
	public Integer getSkillBaseId() {
		return this.skillBaseId;
	}

	public void setSkillBaseId(Integer skillBaseId) {
		this.skillBaseId = skillBaseId;
	}

	@Column(name = "advanced_lv", nullable = false)
	public Integer getAdvancedLv() {
		return this.advancedLv;
	}

	public void setAdvancedLv(Integer advancedLv) {
		this.advancedLv = advancedLv;
	}

	@Column(name = "damage_coefficient", nullable = false, precision = 22, scale = 0)
	public Double getDamageCoefficient() {
		return this.damageCoefficient;
	}

	public void setDamageCoefficient(Double damageCoefficient) {
		this.damageCoefficient = damageCoefficient;
	}

	@Column(name = "add_damage", nullable = false, precision = 22, scale = 0)
	public Double getAddDamage() {
		return this.addDamage;
	}

	public void setAddDamage(Double addDamage) {
		this.addDamage = addDamage;
	}

	@Column(name = "damage", nullable = false, precision = 22, scale = 0)
	public Double getDamage() {
		return this.damage;
	}

	public void setDamage(Double damage) {
		this.damage = damage;
	}

	@Column(name = "next_skill_ext_id", nullable = false)
	public Integer getNextSkillExtId() {
		return this.nextSkillExtId;
	}

	public void setNextSkillExtId(Integer nextSkillExtId) {
		this.nextSkillExtId = nextSkillExtId;
	}

	@Column(name = "info", nullable = false, length = 256)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}