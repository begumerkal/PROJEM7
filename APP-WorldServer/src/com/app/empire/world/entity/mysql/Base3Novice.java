package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Novice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_novice", catalog = "game3")
public class Base3Novice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer moduleSubId;
	private Integer type;
	private String parameter;
	private String info;
	private Integer lv;
	private Integer tag;
	private Integer nextNovice;

	// Constructors

	/** default constructor */
	public Base3Novice() {
	}

	/** full constructor */
	public Base3Novice(Integer id, Integer moduleSubId, Integer type,
			String parameter, String info, Integer lv, Integer tag,
			Integer nextNovice) {
		this.id = id;
		this.moduleSubId = moduleSubId;
		this.type = type;
		this.parameter = parameter;
		this.info = info;
		this.lv = lv;
		this.tag = tag;
		this.nextNovice = nextNovice;
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

	@Column(name = "module_sub_id", nullable = false)
	public Integer getModuleSubId() {
		return this.moduleSubId;
	}

	public void setModuleSubId(Integer moduleSubId) {
		this.moduleSubId = moduleSubId;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "parameter", nullable = false, length = 512)
	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "info", nullable = false, length = 30)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "tag", nullable = false)
	public Integer getTag() {
		return this.tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	@Column(name = "next_novice", nullable = false)
	public Integer getNextNovice() {
		return this.nextNovice;
	}

	public void setNextNovice(Integer nextNovice) {
		this.nextNovice = nextNovice;
	}

}