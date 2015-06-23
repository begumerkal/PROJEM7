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
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_stonerate")
public class StoneRate implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer rate;
	private Integer parm1;
	private Integer parm2;
	private Integer parm3;
	private Integer parm4;
	private Integer type;
	private Integer num;

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
	@Column(name = "rate", precision = 10)
	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Basic()
	@Column(name = "parm1", precision = 10)
	public Integer getParm1() {
		return parm1;
	}

	public void setParm1(Integer parm1) {
		this.parm1 = parm1;
	}

	@Basic()
	@Column(name = "parm2", precision = 10)
	public Integer getParm2() {
		return parm2;
	}

	public void setParm2(Integer parm2) {
		this.parm2 = parm2;
	}

	@Basic()
	@Column(name = "parm3", precision = 10)
	public Integer getParm3() {
		return parm3;
	}

	public void setParm3(Integer parm3) {
		this.parm3 = parm3;
	}

	@Basic()
	@Column(name = "parm4", precision = 10)
	public Integer getParm4() {
		return parm4;
	}

	public void setParm4(Integer parm4) {
		this.parm4 = parm4;
	}

	@Basic()
	@Column(name = "type", precision = 10)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "num", precision = 10)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StoneRate)) {
			return false;
		}
		StoneRate castOther = (StoneRate) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}