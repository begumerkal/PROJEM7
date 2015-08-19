package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapSceneConf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_scene_conf", catalog = "game3")
public class Base1MapSceneConf implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sceneId;
	private String npcId;
	private Integer monsterId;
	private Integer initPro;
	private String restorePro;
	private Integer order;
	private String xy;
	private String pos;
	private String pos1;
	private String trapList;
	private String trapPos;

	// Constructors

	/** default constructor */
	public Base1MapSceneConf() {
	}

	/** full constructor */
	public Base1MapSceneConf(Integer id, Integer sceneId, String npcId,
			Integer monsterId, Integer initPro, String restorePro,
			Integer order, String xy, String pos, String pos1, String trapList,
			String trapPos) {
		this.id = id;
		this.sceneId = sceneId;
		this.npcId = npcId;
		this.monsterId = monsterId;
		this.initPro = initPro;
		this.restorePro = restorePro;
		this.order = order;
		this.xy = xy;
		this.pos = pos;
		this.pos1 = pos1;
		this.trapList = trapList;
		this.trapPos = trapPos;
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

	@Column(name = "scene_id", nullable = false)
	public Integer getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	@Column(name = "npc_id", nullable = false, length = 30)
	public String getNpcId() {
		return this.npcId;
	}

	public void setNpcId(String npcId) {
		this.npcId = npcId;
	}

	@Column(name = "monster_id", nullable = false)
	public Integer getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	@Column(name = "init_pro", nullable = false)
	public Integer getInitPro() {
		return this.initPro;
	}

	public void setInitPro(Integer initPro) {
		this.initPro = initPro;
	}

	@Column(name = "restore_pro", nullable = false, length = 128)
	public String getRestorePro() {
		return this.restorePro;
	}

	public void setRestorePro(String restorePro) {
		this.restorePro = restorePro;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "xy", nullable = false, length = 100)
	public String getXy() {
		return this.xy;
	}

	public void setXy(String xy) {
		this.xy = xy;
	}

	@Column(name = "pos", nullable = false, length = 256)
	public String getPos() {
		return this.pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@Column(name = "pos1", nullable = false, length = 128)
	public String getPos1() {
		return this.pos1;
	}

	public void setPos1(String pos1) {
		this.pos1 = pos1;
	}

	@Column(name = "trap_list", nullable = false, length = 512)
	public String getTrapList() {
		return this.trapList;
	}

	public void setTrapList(String trapList) {
		this.trapList = trapList;
	}

	@Column(name = "trap_pos", nullable = false, length = 512)
	public String getTrapPos() {
		return this.trapPos;
	}

	public void setTrapPos(String trapPos) {
		this.trapPos = trapPos;
	}

}