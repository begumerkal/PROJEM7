package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseGuildSkill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_guild_skill", catalog = "game3")
public class BaseGuildSkill implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer skillBaseId;
	private String lvLimit;
	private String sceneLimit;
	private Integer timeLimit;
	private Integer cdTime;
	private String needActivity;
	private String guildGold;
	private Integer needGuildGoods;
	private String pro;
	private String value;

	// Constructors

	/** default constructor */
	public BaseGuildSkill() {
	}

	/** full constructor */
	public BaseGuildSkill(Integer id, Integer skillBaseId, String lvLimit,
			String sceneLimit, Integer timeLimit, Integer cdTime,
			String needActivity, String guildGold, Integer needGuildGoods,
			String pro, String value) {
		this.id = id;
		this.skillBaseId = skillBaseId;
		this.lvLimit = lvLimit;
		this.sceneLimit = sceneLimit;
		this.timeLimit = timeLimit;
		this.cdTime = cdTime;
		this.needActivity = needActivity;
		this.guildGold = guildGold;
		this.needGuildGoods = needGuildGoods;
		this.pro = pro;
		this.value = value;
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

	@Column(name = "skill_base_id", nullable = false)
	public Integer getSkillBaseId() {
		return this.skillBaseId;
	}

	public void setSkillBaseId(Integer skillBaseId) {
		this.skillBaseId = skillBaseId;
	}

	@Column(name = "lv_limit", nullable = false, length = 50)
	public String getLvLimit() {
		return this.lvLimit;
	}

	public void setLvLimit(String lvLimit) {
		this.lvLimit = lvLimit;
	}

	@Column(name = "scene_limit", nullable = false, length = 50)
	public String getSceneLimit() {
		return this.sceneLimit;
	}

	public void setSceneLimit(String sceneLimit) {
		this.sceneLimit = sceneLimit;
	}

	@Column(name = "time_limit", nullable = false)
	public Integer getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Column(name = "cd_time", nullable = false)
	public Integer getCdTime() {
		return this.cdTime;
	}

	public void setCdTime(Integer cdTime) {
		this.cdTime = cdTime;
	}

	@Column(name = "need_activity", nullable = false, length = 50)
	public String getNeedActivity() {
		return this.needActivity;
	}

	public void setNeedActivity(String needActivity) {
		this.needActivity = needActivity;
	}

	@Column(name = "guild_gold", nullable = false, length = 50)
	public String getGuildGold() {
		return this.guildGold;
	}

	public void setGuildGold(String guildGold) {
		this.guildGold = guildGold;
	}

	@Column(name = "need_guild_goods", nullable = false)
	public Integer getNeedGuildGoods() {
		return this.needGuildGoods;
	}

	public void setNeedGuildGoods(Integer needGuildGoods) {
		this.needGuildGoods = needGuildGoods;
	}

	@Column(name = "pro", nullable = false, length = 256)
	public String getPro() {
		return this.pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	@Column(name = "value", nullable = false, length = 256)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}