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
 * The persistent class for the tab_friend database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_draw_type")
public class DrawType implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer itemId;
	private Integer costNum;
	private Integer accumulatedValue;
	private String bigIcon;
	private String miniIcon;
	private String name;

	public DrawType() {
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
	@Column(name = "itemId", precision = 10)
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Basic()
	@Column(name = "cost_num", precision = 10)
	public Integer getCostNum() {
		return costNum;
	}

	public void setCostNum(Integer costNum) {
		this.costNum = costNum;
	}

	@Basic()
	@Column(name = "accumulated_value", precision = 10)
	public Integer getAccumulatedValue() {
		return accumulatedValue;
	}

	public void setAccumulatedValue(Integer accumulatedValue) {
		this.accumulatedValue = accumulatedValue;
	}

	@Basic()
	@Column(name = "big_icon")
	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	@Basic()
	@Column(name = "mini_icon")
	public String getMiniIcon() {
		return miniIcon;
	}

	public void setMiniIcon(String miniIcon) {
		this.miniIcon = miniIcon;
	}

	@Basic()
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DrawType)) {
			return false;
		}
		DrawType castOther = (DrawType) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}