package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseLanguage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_language")
public class BaseLanguage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String zh;
	private String en;

	// Constructors

	/** default constructor */
	public BaseLanguage() {
	}

	/** full constructor */
	public BaseLanguage(Integer id, String zh, String en, Integer baseAnnouncementId) {
		this.id = id;
		this.zh = zh;
		this.en = en;
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

	@Column(name = "ZH", nullable = false, length = 1024)
	public String getZh() {
		return this.zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	@Column(name = "EN", nullable = false, length = 256)
	public String getEn() {
		return this.en;
	}

	public void setEn(String en) {
		this.en = en;
	}
}