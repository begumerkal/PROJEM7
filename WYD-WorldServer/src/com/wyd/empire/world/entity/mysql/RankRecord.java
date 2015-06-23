package com.wyd.empire.world.entity.mysql;

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
 * The persistent class for the tab_playersinconsortia database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_rankrecord")
public class RankRecord implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int id;
	private int integral;
	private Player player;
	private int winNum;
	private int totalNum;
	private int lastIntegral;

	public RankRecord() {
		player = new Player();
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// bi-directional many-to-one association to Player
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playerId", referencedColumnName = "id")
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Basic()
	@Column(name = "integral", precision = 10)
	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	@Basic()
	@Column(name = "winNum", precision = 10)
	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	@Basic()
	@Column(name = "totalNum", precision = 10)
	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	@Basic()
	@Column(name = "lastIntegral", precision = 10)
	public int getLastIntegral() {
		return lastIntegral;
	}

	public void setLastIntegral(int lastIntegral) {
		this.lastIntegral = lastIntegral;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RankRecord)) {
			return false;
		}
		RankRecord castOther = (RankRecord) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}