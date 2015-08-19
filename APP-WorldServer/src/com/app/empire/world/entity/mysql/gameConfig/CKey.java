package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CKey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_key", catalog = "game3")
public class CKey implements java.io.Serializable {

	// Fields

	private CKeyId id;

	// Constructors

	/** default constructor */
	public CKey() {
	}

	/** full constructor */
	public CKey(CKeyId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "key", column = @Column(name = "key", nullable = false, length = 10)),
			@AttributeOverride(name = "replace", column = @Column(name = "replace", nullable = false, length = 10)) })
	public CKeyId getId() {
		return this.id;
	}

	public void setId(CKeyId id) {
		this.id = id;
	}

}