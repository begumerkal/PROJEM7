package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseQuality entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_quality", catalog = "game3")
public class BaseQuality implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short type;
	private String nameSign;
	private String name;
	private String white;
	private String green;
	private String blue;
	private String yellow;
	private String purple;

	// Constructors

	/** default constructor */
	public BaseQuality() {
	}

	/** full constructor */
	public BaseQuality(Integer id, Short type, String nameSign, String name,
			String white, String green, String blue, String yellow,
			String purple) {
		this.id = id;
		this.type = type;
		this.nameSign = nameSign;
		this.name = name;
		this.white = white;
		this.green = green;
		this.blue = blue;
		this.yellow = yellow;
		this.purple = purple;
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

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "name_sign", nullable = false, length = 20)
	public String getNameSign() {
		return this.nameSign;
	}

	public void setNameSign(String nameSign) {
		this.nameSign = nameSign;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "white", nullable = false, length = 20)
	public String getWhite() {
		return this.white;
	}

	public void setWhite(String white) {
		this.white = white;
	}

	@Column(name = "green", nullable = false, length = 20)
	public String getGreen() {
		return this.green;
	}

	public void setGreen(String green) {
		this.green = green;
	}

	@Column(name = "blue", nullable = false, length = 20)
	public String getBlue() {
		return this.blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}

	@Column(name = "yellow", nullable = false, length = 20)
	public String getYellow() {
		return this.yellow;
	}

	public void setYellow(String yellow) {
		this.yellow = yellow;
	}

	@Column(name = "purple", nullable = false, length = 20)
	public String getPurple() {
		return this.purple;
	}

	public void setPurple(String purple) {
		this.purple = purple;
	}

}