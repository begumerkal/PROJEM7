package com.wyd.empire.world.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_playersinconsortia database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_challengerecord")
public class ChallengeRecord implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int id;
	private int playerId;
	private int integral;
	private int winNum;// 玩家连胜次数
	private int serviceId;
	private int lastIntegral;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "player_id", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
	@Column(name = "win_num", precision = 10)
	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	@Basic()
	@Column(name = "service_id", precision = 10)
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	@Basic()
	@Column(name = "last_integral", precision = 10)
	public int getLastIntegral() {
		return lastIntegral;
	}

	public void setLastIntegral(int lastIntegral) {
		this.lastIntegral = lastIntegral;
	}
}