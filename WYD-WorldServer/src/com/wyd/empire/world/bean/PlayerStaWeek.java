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
 * The persistent class for the log_playerstaweek database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_playerstaweek")
public class PlayerStaWeek implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer exp;
	private Integer gold;
	private Integer goldRank;
	private Integer isWeek;
	private Integer level;
	private Integer levelRank;
	private Integer ticket;
	private Integer ticketRank;
	private Integer winNum;
	private Integer winNumRank;
	private Integer playerId;
	private String areaId;
	private int wrmNum;// 周数或者月数

	private Integer trendRank;

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
	@Column(name = "exp", precision = 10)
	public Integer getExp() {
		return this.exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@Basic()
	@Column(name = "gold", precision = 10)
	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Basic()
	@Column(name = "goldRank", precision = 10)
	public Integer getGoldRank() {
		return this.goldRank;
	}

	public void setGoldRank(Integer goldRank) {
		this.goldRank = goldRank;
	}

	@Basic()
	@Column(name = "isWeek", precision = 10)
	public Integer getIsWeek() {
		return this.isWeek;
	}

	public void setIsWeek(Integer isWeek) {
		this.isWeek = isWeek;
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "levelRank", precision = 10)
	public Integer getLevelRank() {
		return this.levelRank;
	}

	public void setLevelRank(Integer levelRank) {
		this.levelRank = levelRank;
	}

	@Basic()
	@Column(name = "ticket", precision = 10)
	public Integer getTicket() {
		return this.ticket;
	}

	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}

	@Basic()
	@Column(name = "ticketRank", precision = 10)
	public Integer getTicketRank() {
		return this.ticketRank;
	}

	public void setTicketRank(Integer ticketRank) {
		this.ticketRank = ticketRank;
	}

	@Basic()
	@Column(name = "winNum", precision = 10)
	public Integer getWinNum() {
		return this.winNum;
	}

	public void setWinNum(Integer winNum) {
		this.winNum = winNum;
	}

	@Basic()
	@Column(name = "winNumRank", precision = 10)
	public Integer getWinNumRank() {
		return this.winNumRank;
	}

	public void setWinNumRank(Integer winNumRank) {
		this.winNumRank = winNumRank;
	}

	@Basic()
	@Column(name = "playerId", precision = 10)
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "wrm_num", precision = 10)
	public int getWrmNum() {
		return wrmNum;
	}

	public void setWrmNum(int wrmNum) {
		this.wrmNum = wrmNum;
	}

	@Basic()
	@Column(name = "trend_rank", precision = 10)
	public Integer getTrendRank() {
		return trendRank;
	}

	public void setTrendRank(Integer trendRank) {
		this.trendRank = trendRank;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlayerStaWeek)) {
			return false;
		}
		PlayerStaWeek castOther = (PlayerStaWeek) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}