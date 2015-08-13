package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseAnnouncement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_announcement", catalog = "game3")
public class BaseAnnouncement implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mode;
	private Integer pos;
	private String title;
	private String entry;

	// Constructors

	/** default constructor */
	public BaseAnnouncement() {
	}

	/** full constructor */
	public BaseAnnouncement(Integer id, Integer mode, Integer pos,
			String title, String entry) {
		this.id = id;
		this.mode = mode;
		this.pos = pos;
		this.title = title;
		this.entry = entry;
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

	@Column(name = "mode", nullable = false)
	public Integer getMode() {
		return this.mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	@Column(name = "pos", nullable = false)
	public Integer getPos() {
		return this.pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	@Column(name = "title", nullable = false, length = 64)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "entry", nullable = false, length = 128)
	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}