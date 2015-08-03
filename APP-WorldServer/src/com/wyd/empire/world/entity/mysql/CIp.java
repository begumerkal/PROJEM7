package com.wyd.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CIp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_ip", catalog = "game3")
public class CIp implements java.io.Serializable {

	// Fields

	private CIpId id;

	// Constructors

	/** default constructor */
	public CIp() {
	}

	/** full constructor */
	public CIp(CIpId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "ip", column = @Column(name = "ip", nullable = false)),
			@AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)),
			@AttributeOverride(name = "time1", column = @Column(name = "time1", nullable = false)),
			@AttributeOverride(name = "time2", column = @Column(name = "time2", nullable = false)) })
	public CIpId getId() {
		return this.id;
	}

	public void setId(CIpId id) {
		this.id = id;
	}

}