package com.wyd.empire.world.bean;

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
 * The persistent class for the tab_exchange database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_exchangerate")
public class ExchangeRate implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer day;
	private Integer payType;
	private Integer startRate;
	private Integer endRate;
	private Integer realRate;
	private Integer headPrice;
	private Integer facePrice;
	private Integer bodyPrice;
	private Integer wpPrice;

	public ExchangeRate() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "day", nullable = false, precision = 10)
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	@Basic()
	@Column(name = "payType", nullable = false, precision = 10)
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Basic()
	@Column(name = "startRate", nullable = false, precision = 10)
	public Integer getStartRate() {
		return startRate;
	}

	public void setStartRate(Integer startRate) {
		this.startRate = startRate;
	}

	@Basic()
	@Column(name = "endRate", nullable = false, precision = 10)
	public Integer getEndRate() {
		return endRate;
	}

	public void setEndRate(Integer endRate) {
		this.endRate = endRate;
	}

	@Basic()
	@Column(name = "realRate", nullable = false, precision = 10)
	public Integer getRealRate() {
		return realRate;
	}

	public void setRealRate(Integer realRate) {
		this.realRate = realRate;
	}

	@Basic()
	@Column(name = "headPrice", nullable = false, precision = 10)
	public Integer getHeadPrice() {
		return headPrice;
	}

	public void setHeadPrice(Integer headPrice) {
		this.headPrice = headPrice;
	}

	@Basic()
	@Column(name = "facePrice", nullable = false, precision = 10)
	public Integer getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(Integer facePrice) {
		this.facePrice = facePrice;
	}

	@Basic()
	@Column(name = "bodyPrice", nullable = false, precision = 10)
	public Integer getBodyPrice() {
		return bodyPrice;
	}

	public void setBodyPrice(Integer bodyPrice) {
		this.bodyPrice = bodyPrice;
	}

	@Basic()
	@Column(name = "wpPrice", nullable = false, precision = 10)
	public Integer getWpPrice() {
		return wpPrice;
	}

	public void setWpPrice(Integer wpPrice) {
		this.wpPrice = wpPrice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExchangeRate)) {
			return false;
		}
		ExchangeRate castOther = (ExchangeRate) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}