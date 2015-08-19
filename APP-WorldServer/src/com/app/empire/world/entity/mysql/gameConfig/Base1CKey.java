package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1CKey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_c_key", catalog = "game3")
public class Base1CKey implements java.io.Serializable {

	// Fields

	private Integer id;
	private String key;
	private String allkey;

	// Constructors

	/** default constructor */
	public Base1CKey() {
	}

	/** full constructor */
	public Base1CKey(Integer id, String key, String allkey) {
		this.id = id;
		this.key = key;
		this.allkey = allkey;
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

	@Column(name = "key", nullable = false, length = 250)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "allkey", nullable = false, length = 250)
	public String getAllkey() {
		return this.allkey;
	}

	public void setAllkey(String allkey) {
		this.allkey = allkey;
	}

}