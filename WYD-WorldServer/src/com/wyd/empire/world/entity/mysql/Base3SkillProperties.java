package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3SkillProperties entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_skill_properties", catalog = "game3")
public class Base3SkillProperties implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer skillExtId;
	private String properties;
	private Double coefficient;
	private Integer actType;

	// Constructors

	/** default constructor */
	public Base3SkillProperties() {
	}

	/** full constructor */
	public Base3SkillProperties(Integer id, Integer skillExtId,
			String properties, Double coefficient, Integer actType) {
		this.id = id;
		this.skillExtId = skillExtId;
		this.properties = properties;
		this.coefficient = coefficient;
		this.actType = actType;
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

	@Column(name = "skill_ext_id", nullable = false)
	public Integer getSkillExtId() {
		return this.skillExtId;
	}

	public void setSkillExtId(Integer skillExtId) {
		this.skillExtId = skillExtId;
	}

	@Column(name = "properties", nullable = false, length = 256)
	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Column(name = "coefficient", nullable = false, precision = 22, scale = 0)
	public Double getCoefficient() {
		return this.coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name = "act_type", nullable = false)
	public Integer getActType() {
		return this.actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

}