package com.wyd.empire.world.entity.mysql;

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
 * 玩家单人副本表.
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_player_singlemap")
public class PlayerSingleMap implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer id; //
	private int playerId; // 玩家ID
	private int mapId; // 地图表
	private int passTimes; // 通关次数 -1从未通关
	private int totalPassTimes;// 总通关次数（不会清零）
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Date resetTime; // 重置时间

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "player_id")
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "createTime")
	public Date getCreateTime() {
		createTime = null == createTime ? new Date(System.currentTimeMillis()) : createTime;
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic()
	@Column(name = "map_id")
	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
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

	@Basic()
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		updateTime = null == updateTime ? new Date(System.currentTimeMillis()) : updateTime;
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic()
	@Column(name = "resetTime")
	public Date getResetTime() {
		return resetTime;
	}

	public void setResetTime(Date resetTime) {
		this.resetTime = resetTime;
	}

}
