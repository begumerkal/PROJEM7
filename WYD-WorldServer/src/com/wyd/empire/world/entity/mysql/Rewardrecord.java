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
 * The persistent class for the tab_rewardrecord database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_rewardrecord")
public class Rewardrecord implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date time;
	private Integer playerId;
	private Integer vipMark;
	private String areaId;
	private String remark;
	private Integer receiveType; // 领取类型

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
	@Column(name = "playerId", precision = 10)
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Basic()
	@Column(name = "vipMark", precision = 2)
	public Integer getVipMark() {
		return vipMark;
	}

	public void setVipMark(Integer vipMark) {
		this.vipMark = vipMark;
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
	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic()
	@Column(name = "receiveType", precision = 2)
	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

}
