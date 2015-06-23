package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the log_propsskillsused database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_propsskillsused")
public class PropsSkillsUsed implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date inday;
	private Integer type;
	private Integer usedTimes;
	private Tools tool;

	public PropsSkillsUsed() {
		tool = new Tools();
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
	@Column(name = "inday")
	public Date getInday() {
		return this.inday;
	}

	public void setInday(Date inday) {
		this.inday = inday;
	}

	@Basic()
	@Column(name = "type", precision = 10)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "usedTimes", precision = 10)
	public Integer getUsedTimes() {
		return this.usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	// bi-directional many-to-one association to Tool
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toolsId", referencedColumnName = "id", nullable = false)
	public Tools getTool() {
		return this.tool;
	}

	public void setTool(Tools tool) {
		this.tool = tool;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PropsSkillsUsed)) {
			return false;
		}
		PropsSkillsUsed castOther = (PropsSkillsUsed) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}