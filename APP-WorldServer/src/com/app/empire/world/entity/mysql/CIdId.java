package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CIdId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CIdId implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private Integer time1;
	private Integer time2;

	// Constructors

	/** default constructor */
	public CIdId() {
	}

	/** full constructor */
	public CIdId(Integer id, Integer type, Integer time1, Integer time2) {
		this.id = id;
		this.type = type;
		this.time1 = time1;
		this.time2 = time2;
	}

	// Property accessors

	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "time1", nullable = false)
	public Integer getTime1() {
		return this.time1;
	}

	public void setTime1(Integer time1) {
		this.time1 = time1;
	}

	@Column(name = "time2", nullable = false)
	public Integer getTime2() {
		return this.time2;
	}

	public void setTime2(Integer time2) {
		this.time2 = time2;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CIdId))
			return false;
		CIdId castOther = (CIdId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getTime1() == castOther.getTime1()) || (this
						.getTime1() != null && castOther.getTime1() != null && this
						.getTime1().equals(castOther.getTime1())))
				&& ((this.getTime2() == castOther.getTime2()) || (this
						.getTime2() != null && castOther.getTime2() != null && this
						.getTime2().equals(castOther.getTime2())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getTime1() == null ? 0 : this.getTime1().hashCode());
		result = 37 * result
				+ (getTime2() == null ? 0 : this.getTime2().hashCode());
		return result;
	}

}