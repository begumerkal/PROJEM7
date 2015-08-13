package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Skill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_skill", catalog = "game3")
public class Base3Skill implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer skillType;
	private Integer order;
	private Integer proTpye;
	private Integer lvCoefficient;
	private Float forceCoefficient;

	// Constructors

	/** default constructor */
	public Base3Skill() {
	}

	/** full constructor */
	public Base3Skill(Integer id, String name, Integer skillType,
			Integer order, Integer proTpye, Integer lvCoefficient,
			Float forceCoefficient) {
		this.id = id;
		this.name = name;
		this.skillType = skillType;
		this.order = order;
		this.proTpye = proTpye;
		this.lvCoefficient = lvCoefficient;
		this.forceCoefficient = forceCoefficient;
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "skill_type", nullable = false)
	public Integer getSkillType() {
		return this.skillType;
	}

	public void setSkillType(Integer skillType) {
		this.skillType = skillType;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "pro_tpye", nullable = false)
	public Integer getProTpye() {
		return this.proTpye;
	}

	public void setProTpye(Integer proTpye) {
		this.proTpye = proTpye;
	}

	@Column(name = "lv_coefficient", nullable = false)
	public Integer getLvCoefficient() {
		return this.lvCoefficient;
	}

	public void setLvCoefficient(Integer lvCoefficient) {
		this.lvCoefficient = lvCoefficient;
	}

	@Column(name = "force_coefficient", nullable = false, precision = 12, scale = 0)
	public Float getForceCoefficient() {
		return this.forceCoefficient;
	}

	public void setForceCoefficient(Float forceCoefficient) {
		this.forceCoefficient = forceCoefficient;
	}

}