package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_consortiaright database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_consortiaright")
public class ConsortiaRight implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer position;
	private Integer authority;

	public ConsortiaRight() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "position", unique = true, nullable = false, precision = 10)
	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Basic()
	@Column(name = "authority", precision = 10)
	public Integer getAuthority() {
		return this.authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConsortiaRight)) {
			return false;
		}
		ConsortiaRight castOther = (ConsortiaRight) other;
		return new EqualsBuilder().append(this.getPosition(), castOther.getPosition()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getPosition()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("position", getPosition()).toString();
	}
}