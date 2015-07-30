package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseModulePack entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_module_pack", catalog = "game3")
public class BaseModulePack implements java.io.Serializable {

	// Fields

	private Integer id;
	private String modulename;
	private Integer level;
	private String subname;

	// Constructors

	/** default constructor */
	public BaseModulePack() {
	}

	/** full constructor */
	public BaseModulePack(Integer id, String modulename, Integer level,
			String subname) {
		this.id = id;
		this.modulename = modulename;
		this.level = level;
		this.subname = subname;
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

	@Column(name = "modulename", nullable = false, length = 20)
	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "subname", nullable = false, length = 20)
	public String getSubname() {
		return this.subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

}