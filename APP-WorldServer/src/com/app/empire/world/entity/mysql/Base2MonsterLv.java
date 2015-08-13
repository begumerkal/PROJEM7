package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base2MonsterLv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base2_monster_lv", catalog = "game3")
public class Base2MonsterLv implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lv;
	private String property;

	// Constructors

	/** default constructor */
	public Base2MonsterLv() {
	}

	/** full constructor */
	public Base2MonsterLv(Integer id, Integer lv, String property) {
		this.id = id;
		this.lv = lv;
		this.property = property;
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

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "property", nullable = false, length = 1024)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}