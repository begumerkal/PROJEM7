package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.Date;

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
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_marryrecord")
public class MarryRecord implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int manId;
	private int womanId;
	private int statusMode;
	private Date engagedTime;
	private int useItemId;
	private Date marryTime;
	private int type;
	private Date createTime;
	private int dhId;
	private int jhId;

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
	@Column(name = "manId", precision = 10)
	public int getManId() {
		return manId;
	}

	public void setManId(int manId) {
		this.manId = manId;
	}

	@Basic()
	@Column(name = "womanId", precision = 10)
	public int getWomanId() {
		return womanId;
	}

	public void setWomanId(int womanId) {
		this.womanId = womanId;
	}

	@Basic()
	@Column(name = "statusMode", precision = 10)
	public int getStatusMode() {
		return statusMode;
	}

	public void setStatusMode(int statusMode) {
		this.statusMode = statusMode;
	}

	@Basic()
	@Column(name = "engagedTime")
	public Date getEngagedTime() {
		return engagedTime;
	}

	public void setEngagedTime(Date engagedTime) {
		this.engagedTime = engagedTime;
	}

	@Basic()
	@Column(name = "useItemId", precision = 10)
	public int getUseItemId() {
		return useItemId;
	}

	public void setUseItemId(int useItemId) {
		this.useItemId = useItemId;
	}

	@Basic()
	@Column(name = "marryTime")
	public Date getMarryTime() {
		return marryTime;
	}

	public void setMarryTime(Date marryTime) {
		this.marryTime = marryTime;
	}

	@Basic()
	@Column(name = "type", precision = 10)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic()
	@Column(name = "dhId", precision = 10)
	public int getDhId() {
		return dhId;
	}

	public void setDhId(int dhId) {
		this.dhId = dhId;
	}

	@Basic()
	@Column(name = "jhId", precision = 10)
	public int getJhId() {
		return jhId;
	}

	public void setJhId(int jhId) {
		this.jhId = jhId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MarryRecord)) {
			return false;
		}
		MarryRecord castOther = (MarryRecord) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}