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
 * The persistent class for the tab_tools database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_tools")
public class Tools implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer consumePower;
	private String desc;
	private String icon;
	private String name;
	private Integer param1;
	private Integer param2;
	private Integer priceCostGold;
	private Integer sort;
	private Integer specialAttackParam;
	private Integer specialAttackType;
	private Integer subtype;
	private Integer tireValue;
	private Integer type;

	public Tools() {
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
	@Column(name = "consumePower", precision = 10)
	public Integer getConsumePower() {
		return this.consumePower;
	}

	public void setConsumePower(Integer consumePower) {
		this.consumePower = consumePower;
	}

	@Basic()
	@Column(name = "tool_desc", length = 128)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Basic()
	@Column(name = "icon", length = 256)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "name", length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "param1", precision = 10)
	public Integer getParam1() {
		return this.param1;
	}

	public void setParam1(Integer param1) {
		this.param1 = param1;
	}

	@Basic()
	@Column(name = "param2", precision = 10)
	public Integer getParam2() {
		return this.param2;
	}

	public void setParam2(Integer param2) {
		this.param2 = param2;
	}

	@Basic()
	@Column(name = "priceCostGold", precision = 10)
	public Integer getPriceCostGold() {
		return this.priceCostGold;
	}

	public void setPriceCostGold(Integer priceCostGold) {
		this.priceCostGold = priceCostGold;
	}

	@Basic()
	@Column(name = "sort", precision = 10)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Basic()
	@Column(name = "specialAttackParam", precision = 10)
	public Integer getSpecialAttackParam() {
		return this.specialAttackParam;
	}

	public void setSpecialAttackParam(Integer specialAttackParam) {
		this.specialAttackParam = specialAttackParam;
	}

	@Basic()
	@Column(name = "specialAttackType", precision = 10)
	public Integer getSpecialAttackType() {
		return this.specialAttackType;
	}

	public void setSpecialAttackType(Integer specialAttackType) {
		this.specialAttackType = specialAttackType;
	}

	@Basic()
	@Column(name = "subtype", precision = 3)
	public Integer getSubtype() {
		return this.subtype;
	}

	public void setSubtype(Integer subtype) {
		this.subtype = subtype;
	}

	@Basic()
	@Column(name = "tireValue", precision = 10)
	public Integer getTireValue() {
		return this.tireValue;
	}

	public void setTireValue(Integer tireValue) {
		this.tireValue = tireValue;
	}

	@Basic()
	@Column(name = "type", precision = 3)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void checkingData() {
		if (null == consumePower) {
			consumePower = 0;
		}
		if (null == desc) {
			desc = "";
		}
		if (null == icon) {
			icon = "";
		}
		if (null == name) {
			name = "";
		}
		if (null == param1) {
			param1 = 0;
		}
		if (null == param2) {
			param2 = 0;
		}
		if (null == priceCostGold) {
			priceCostGold = 0;
		}
		if (null == subtype) {
			subtype = 0;
		}
		if (null == tireValue) {
			tireValue = 0;
		}
		if (null == type) {
			type = 0;
		}
		if (null == specialAttackType) {
			specialAttackType = 0;
		}
		if (null == specialAttackParam) {
			specialAttackParam = 0;
		}
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Tools)) {
			return false;
		}
		Tools castOther = (Tools) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}