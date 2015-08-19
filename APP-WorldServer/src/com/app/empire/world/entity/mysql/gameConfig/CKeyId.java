package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CKeyId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CKeyId implements java.io.Serializable {

	// Fields

	private String key;
	private String replace;

	// Constructors

	/** default constructor */
	public CKeyId() {
	}

	/** full constructor */
	public CKeyId(String key, String replace) {
		this.key = key;
		this.replace = replace;
	}

	// Property accessors

	@Column(name = "key", nullable = false, length = 10)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "replace", nullable = false, length = 10)
	public String getReplace() {
		return this.replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CKeyId))
			return false;
		CKeyId castOther = (CKeyId) other;

		return ((this.getKey() == castOther.getKey()) || (this.getKey() != null
				&& castOther.getKey() != null && this.getKey().equals(
				castOther.getKey())))
				&& ((this.getReplace() == castOther.getReplace()) || (this
						.getReplace() != null && castOther.getReplace() != null && this
						.getReplace().equals(castOther.getReplace())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKey() == null ? 0 : this.getKey().hashCode());
		result = 37 * result
				+ (getReplace() == null ? 0 : this.getReplace().hashCode());
		return result;
	}

}