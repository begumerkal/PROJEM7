package com.wyd.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_id", catalog = "game3")
public class CId implements java.io.Serializable {

	// Fields

	private CIdId id;

	// Constructors

	/** default constructor */
	public CId() {
	}

	/** full constructor */
	public CId(CIdId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
			@AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)),
			@AttributeOverride(name = "time1", column = @Column(name = "time1", nullable = false)),
			@AttributeOverride(name = "time2", column = @Column(name = "time2", nullable = false)) })
	public CIdId getId() {
		return this.id;
	}

	public void setId(CIdId id) {
		this.id = id;
	}

}