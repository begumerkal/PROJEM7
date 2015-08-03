package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Plot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_plot", catalog = "game3")
public class Base3Plot implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer grop;
	private String next;
	private Integer type;
	private Integer mapId;
	private Integer actionType;
	private Float actionTime;
	private Integer npcId;
	private String action;

	// Constructors

	/** default constructor */
	public Base3Plot() {
	}

	/** full constructor */
	public Base3Plot(Integer id, Integer grop, String next, Integer type,
			Integer mapId, Integer actionType, Float actionTime, Integer npcId,
			String action) {
		this.id = id;
		this.grop = grop;
		this.next = next;
		this.type = type;
		this.mapId = mapId;
		this.actionType = actionType;
		this.actionTime = actionTime;
		this.npcId = npcId;
		this.action = action;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "grop", nullable = false)
	public Integer getGrop() {
		return this.grop;
	}

	public void setGrop(Integer grop) {
		this.grop = grop;
	}

	@Column(name = "next", nullable = false, length = 20)
	public String getNext() {
		return this.next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "map_id", nullable = false)
	public Integer getMapId() {
		return this.mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	@Column(name = "action_type", nullable = false)
	public Integer getActionType() {
		return this.actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	@Column(name = "action_time", nullable = false, precision = 12, scale = 0)
	public Float getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(Float actionTime) {
		this.actionTime = actionTime;
	}

	@Column(name = "npc_id", nullable = false)
	public Integer getNpcId() {
		return this.npcId;
	}

	public void setNpcId(Integer npcId) {
		this.npcId = npcId;
	}

	@Column(name = "action", nullable = false, length = 65535)
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}