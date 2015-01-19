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

/**
 * The persistent class for the tab_colddown database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_player_bossmap")
public class PlayerBossmap implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int star;
	private Date lastPlayTime;
	private int playerId;
	private int mapId;
	private Date createTime; // 创建时间
	private Date resetTime; // 重置时间
	private int passTimes; // 通关次数 -1从未通关
	private int totalPassTimes;// 总通关次数（不会清零）

	public PlayerBossmap() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "star", precision = 10)
	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	@Basic()
	@Column(name = "lastPlayTime")
	public Date getLastPlayTime() {
		return lastPlayTime;
	}

	public void setLastPlayTime(Date lastPlayTime) {
		this.lastPlayTime = lastPlayTime;
	}

	@Basic()
	@Column(name = "playerId", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "bossMapId", precision = 10)
	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
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
	@Column(name = "resetTime")
	public Date getResetTime() {
		return resetTime;
	}

	public void setResetTime(Date resetTime) {
		this.resetTime = resetTime;
	}

	@Basic()
	@Column(name = "pass_times")
	public int getPassTimes() {
		return passTimes;
	}

	public void setPassTimes(int passTimes) {
		this.passTimes = passTimes;
	}

	@Basic()
	@Column(name = "total_pass_times")
	public int getTotalPassTimes() {
		return totalPassTimes;
	}

	public void setTotalPassTimes(int totalPassTimes) {
		this.totalPassTimes = totalPassTimes;
	}
}