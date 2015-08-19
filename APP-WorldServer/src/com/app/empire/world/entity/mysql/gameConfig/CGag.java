package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CGag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_gag", catalog = "game3")
public class CGag implements java.io.Serializable {

	// Fields

	private CGagId id;

	// Constructors

	/** default constructor */
	public CGag() {
	}

	/** full constructor */
	public CGag(CGagId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "ip", column = @Column(name = "ip", nullable = false)),
			@AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)),
			@AttributeOverride(name = "time1", column = @Column(name = "time1", nullable = false)),
			@AttributeOverride(name = "time2", column = @Column(name = "time2", nullable = false)) })
	public CGagId getId() {
		return this.id;
	}

	public void setId(CGagId id) {
		this.id = id;
	}

}