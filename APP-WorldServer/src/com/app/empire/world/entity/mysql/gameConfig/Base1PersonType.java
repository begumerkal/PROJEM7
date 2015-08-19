package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1PersonType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_person_type", catalog = "game3")
public class Base1PersonType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String personIntergal;
	private Float baseDrop;
	private Float stoneDrop;
	private Float getequipDrop;
	private Float lackequipDrop;
	private Float cdDrop;

	// Constructors

	/** default constructor */
	public Base1PersonType() {
	}

	/** full constructor */
	public Base1PersonType(Integer id, String personIntergal, Float baseDrop,
			Float stoneDrop, Float getequipDrop, Float lackequipDrop,
			Float cdDrop) {
		this.id = id;
		this.personIntergal = personIntergal;
		this.baseDrop = baseDrop;
		this.stoneDrop = stoneDrop;
		this.getequipDrop = getequipDrop;
		this.lackequipDrop = lackequipDrop;
		this.cdDrop = cdDrop;
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

	@Column(name = "person_intergal", nullable = false, length = 128)
	public String getPersonIntergal() {
		return this.personIntergal;
	}

	public void setPersonIntergal(String personIntergal) {
		this.personIntergal = personIntergal;
	}

	@Column(name = "base_drop", nullable = false, precision = 12, scale = 0)
	public Float getBaseDrop() {
		return this.baseDrop;
	}

	public void setBaseDrop(Float baseDrop) {
		this.baseDrop = baseDrop;
	}

	@Column(name = "stone_drop", nullable = false, precision = 12, scale = 0)
	public Float getStoneDrop() {
		return this.stoneDrop;
	}

	public void setStoneDrop(Float stoneDrop) {
		this.stoneDrop = stoneDrop;
	}

	@Column(name = "getequip_drop", nullable = false, precision = 12, scale = 0)
	public Float getGetequipDrop() {
		return this.getequipDrop;
	}

	public void setGetequipDrop(Float getequipDrop) {
		this.getequipDrop = getequipDrop;
	}

	@Column(name = "lackequip_drop", nullable = false, precision = 12, scale = 0)
	public Float getLackequipDrop() {
		return this.lackequipDrop;
	}

	public void setLackequipDrop(Float lackequipDrop) {
		this.lackequipDrop = lackequipDrop;
	}

	@Column(name = "cd_drop", nullable = false, precision = 12, scale = 0)
	public Float getCdDrop() {
		return this.cdDrop;
	}

	public void setCdDrop(Float cdDrop) {
		this.cdDrop = cdDrop;
	}

}