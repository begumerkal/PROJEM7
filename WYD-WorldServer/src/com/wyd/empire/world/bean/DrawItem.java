package com.wyd.empire.world.bean;

import java.io.Serializable;

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
 * The persistent class for the tab_exchange database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_draw_item")
public class DrawItem implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private DrawReward drawReward;
	private Integer playerId;
	private Integer type;

	public DrawItem() {
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
	@Column(name = "playerId", nullable = false, precision = 10)
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	// bi-directional many-to-one association to ShopItem
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rewardId", referencedColumnName = "id", nullable = false)
	public DrawReward getDrawReward() {
		return drawReward;
	}

	public void setDrawReward(DrawReward drawReward) {
		this.drawReward = drawReward;
	}

	@Basic()
	@Column(name = "type", nullable = false, precision = 10)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DrawItem)) {
			return false;
		}
		DrawItem castOther = (DrawItem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}