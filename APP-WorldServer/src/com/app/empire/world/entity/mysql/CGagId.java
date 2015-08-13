package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CGagId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CGagId implements java.io.Serializable {

	// Fields

	private Integer ip;
	private Integer type;
	private Integer time1;
	private Integer time2;

	// Constructors

	/** default constructor */
	public CGagId() {
	}

	/** full constructor */
	public CGagId(Integer ip, Integer type, Integer time1, Integer time2) {
		this.ip = ip;
		this.type = type;
		this.time1 = time1;
		this.time2 = time2;
	}

	// Property accessors

	@Column(name = "ip", nullable = false)
	public Integer getIp() {
		return this.ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
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
		if (!(other instanceof CGagId))
			return false;
		CGagId castOther = (CGagId) other;

		return ((this.getIp() == castOther.getIp()) || (this.getIp() != null
				&& castOther.getIp() != null && this.getIp().equals(
				castOther.getIp())))
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

		result = 37 * result + (getIp() == null ? 0 : this.getIp().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getTime1() == null ? 0 : this.getTime1().hashCode());
		result = 37 * result
				+ (getTime2() == null ? 0 : this.getTime2().hashCode());
		return result;
	}

}