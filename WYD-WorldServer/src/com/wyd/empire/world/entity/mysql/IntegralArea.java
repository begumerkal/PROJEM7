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
 * The persistent class for the tab_friend database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_integral_area")
public class IntegralArea implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int rank_low;
	private int rank_high;
	private int reward_prop_id;

	public IntegralArea() {
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
	@Column(name = "rank_low", precision = 10)
	public int getRank_low() {
		return rank_low;
	}

	public void setRank_low(int rank_low) {
		this.rank_low = rank_low;
	}

	@Basic()
	@Column(name = "rank_high", precision = 10)
	public int getRank_high() {
		return rank_high;
	}

	public void setRank_high(int rank_high) {
		this.rank_high = rank_high;
	}

	@Basic()
	@Column(name = "reward_prop_id", precision = 10)
	public int getReward_prop_id() {
		return reward_prop_id;
	}

	public void setReward_prop_id(int reward_prop_id) {
		this.reward_prop_id = reward_prop_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IntegralArea)) {
			return false;
		}
		IntegralArea castOther = (IntegralArea) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}