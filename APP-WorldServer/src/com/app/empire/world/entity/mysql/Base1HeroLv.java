package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1HeroLv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_hero_lv", catalog = "game3")
public class Base1HeroLv implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer experienceTop;

	// Constructors

	/** default constructor */
	public Base1HeroLv() {
	}

	/** full constructor */
	public Base1HeroLv(Integer id, Integer experienceTop) {
		this.id = id;
		this.experienceTop = experienceTop;
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

	@Column(name = "experience_top", nullable = false)
	public Integer getExperienceTop() {
		return this.experienceTop;
	}

	public void setExperienceTop(Integer experienceTop) {
		this.experienceTop = experienceTop;
	}

}