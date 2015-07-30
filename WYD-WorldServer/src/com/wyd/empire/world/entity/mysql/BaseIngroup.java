package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseIngroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_ingroup", catalog = "game3")
public class BaseIngroup implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;

	// Constructors

	/** default constructor */
	public BaseIngroup() {
	}

	/** full constructor */
	public BaseIngroup(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	@Column(name = "name", nullable = false, length = 36)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}