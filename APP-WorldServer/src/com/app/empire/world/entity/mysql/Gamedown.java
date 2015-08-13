package com.app.empire.world.entity.mysql;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Gamedown entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gamedown", catalog = "game3")
public class Gamedown implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer count;
	private Date date;
	private String platforms;

	// Constructors

	/** default constructor */
	public Gamedown() {
	}

	/** full constructor */
	public Gamedown(Integer id, Integer count, Date date, String platforms) {
		this.id = id;
		this.count = count;
		this.date = date;
		this.platforms = platforms;
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

	@Column(name = "count", nullable = false)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 0)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "platforms", nullable = false, length = 15)
	public String getPlatforms() {
		return this.platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

}