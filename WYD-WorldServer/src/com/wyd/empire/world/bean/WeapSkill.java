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
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_weapskill")
public class WeapSkill implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String skillName;
	private String remark;
	private Integer type;
	private Integer startChance; // 开始概率
	private Integer endChance; // 结束概率
	private Integer realChance; // 实际概率
	private Integer useChance; // 触发概率
	private Integer param1; // 参数1
	private Integer param2; // 参数2
	private Integer level;
	private Integer canGet; // 是否可以被洗练

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
	@Column(name = "type", precision = 4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "skillName")
	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Basic()
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic()
	@Column(name = "startChance", precision = 10)
	public Integer getStartChance() {
		return startChance;
	}

	public void setStartChance(Integer startChance) {
		this.startChance = startChance;
	}

	@Basic()
	@Column(name = "endChance", precision = 10)
	public Integer getEndChance() {
		return endChance;
	}

	public void setEndChance(Integer endChance) {
		this.endChance = endChance;
	}

	@Basic()
	@Column(name = "chance", precision = 10)
	public Integer getRealChance() {
		return realChance;
	}

	public void setRealChance(Integer realChance) {
		this.realChance = realChance;
	}

	@Basic()
	@Column(name = "useChance", precision = 10)
	public Integer getUseChance() {
		return useChance;
	}

	public void setUseChance(Integer useChance) {
		this.useChance = useChance;
	}

	@Basic()
	@Column(name = "param1", precision = 10)
	public Integer getParam1() {
		return param1;
	}

	public void setParam1(Integer param1) {
		this.param1 = param1;
	}

	@Basic()
	@Column(name = "param2", precision = 10)
	public Integer getParam2() {
		return param2;
	}

	public void setParam2(Integer param2) {
		this.param2 = param2;
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "can_get", precision = 10)
	public Integer getCanGet() {
		return canGet;
	}

	public void setCanGet(Integer canGet) {
		this.canGet = canGet;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WeapSkill)) {
			return false;
		}
		WeapSkill castOther = (WeapSkill) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}