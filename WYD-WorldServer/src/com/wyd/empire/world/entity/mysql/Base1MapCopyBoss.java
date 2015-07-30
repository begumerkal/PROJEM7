package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapCopyBoss entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_copy_boss", catalog = "game3")
public class Base1MapCopyBoss implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer showId;
	private Integer sceneId;
	private Integer CUserLv;
	private Integer CEnergyA;
	private Integer CEnergyB;
	private Integer CGold;
	private Integer OPassCondition;
	private Integer startTime;
	private Integer endTime;
	private String AGold;
	private Integer AExperience;
	private String starType;
	private Integer mapLv;
	private String showMonster;
	private Integer status;
	private String descript;
	private String goodsId;
	private Integer monsterLv;
	private Integer monsterStar;
	private Integer monsterQuality;
	private Integer firstDrop;

	// Constructors

	/** default constructor */
	public Base1MapCopyBoss() {
	}

	/** full constructor */
	public Base1MapCopyBoss(Integer id, String name, Integer showId,
			Integer sceneId, Integer CUserLv, Integer CEnergyA,
			Integer CEnergyB, Integer CGold, Integer OPassCondition,
			Integer startTime, Integer endTime, String AGold,
			Integer AExperience, String starType, Integer mapLv,
			String showMonster, Integer status, String descript,
			String goodsId, Integer monsterLv, Integer monsterStar,
			Integer monsterQuality, Integer firstDrop) {
		this.id = id;
		this.name = name;
		this.showId = showId;
		this.sceneId = sceneId;
		this.CUserLv = CUserLv;
		this.CEnergyA = CEnergyA;
		this.CEnergyB = CEnergyB;
		this.CGold = CGold;
		this.OPassCondition = OPassCondition;
		this.startTime = startTime;
		this.endTime = endTime;
		this.AGold = AGold;
		this.AExperience = AExperience;
		this.starType = starType;
		this.mapLv = mapLv;
		this.showMonster = showMonster;
		this.status = status;
		this.descript = descript;
		this.goodsId = goodsId;
		this.monsterLv = monsterLv;
		this.monsterStar = monsterStar;
		this.monsterQuality = monsterQuality;
		this.firstDrop = firstDrop;
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "show_id", nullable = false)
	public Integer getShowId() {
		return this.showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	@Column(name = "scene_id", nullable = false)
	public Integer getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	@Column(name = "c_user_lv", nullable = false)
	public Integer getCUserLv() {
		return this.CUserLv;
	}

	public void setCUserLv(Integer CUserLv) {
		this.CUserLv = CUserLv;
	}

	@Column(name = "c_energy_a", nullable = false)
	public Integer getCEnergyA() {
		return this.CEnergyA;
	}

	public void setCEnergyA(Integer CEnergyA) {
		this.CEnergyA = CEnergyA;
	}

	@Column(name = "c_energy_b", nullable = false)
	public Integer getCEnergyB() {
		return this.CEnergyB;
	}

	public void setCEnergyB(Integer CEnergyB) {
		this.CEnergyB = CEnergyB;
	}

	@Column(name = "c_gold", nullable = false)
	public Integer getCGold() {
		return this.CGold;
	}

	public void setCGold(Integer CGold) {
		this.CGold = CGold;
	}

	@Column(name = "o_pass_condition", nullable = false)
	public Integer getOPassCondition() {
		return this.OPassCondition;
	}

	public void setOPassCondition(Integer OPassCondition) {
		this.OPassCondition = OPassCondition;
	}

	@Column(name = "start_time", nullable = false)
	public Integer getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = false)
	public Integer getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	@Column(name = "a_gold", nullable = false, length = 300)
	public String getAGold() {
		return this.AGold;
	}

	public void setAGold(String AGold) {
		this.AGold = AGold;
	}

	@Column(name = "a_experience", nullable = false)
	public Integer getAExperience() {
		return this.AExperience;
	}

	public void setAExperience(Integer AExperience) {
		this.AExperience = AExperience;
	}

	@Column(name = "star_type", nullable = false, length = 32)
	public String getStarType() {
		return this.starType;
	}

	public void setStarType(String starType) {
		this.starType = starType;
	}

	@Column(name = "map_lv", nullable = false)
	public Integer getMapLv() {
		return this.mapLv;
	}

	public void setMapLv(Integer mapLv) {
		this.mapLv = mapLv;
	}

	@Column(name = "show_monster", nullable = false, length = 256)
	public String getShowMonster() {
		return this.showMonster;
	}

	public void setShowMonster(String showMonster) {
		this.showMonster = showMonster;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "descript", nullable = false, length = 128)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "goods_id", nullable = false, length = 125)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "monster_lv", nullable = false)
	public Integer getMonsterLv() {
		return this.monsterLv;
	}

	public void setMonsterLv(Integer monsterLv) {
		this.monsterLv = monsterLv;
	}

	@Column(name = "monster_star", nullable = false)
	public Integer getMonsterStar() {
		return this.monsterStar;
	}

	public void setMonsterStar(Integer monsterStar) {
		this.monsterStar = monsterStar;
	}

	@Column(name = "monster_quality", nullable = false)
	public Integer getMonsterQuality() {
		return this.monsterQuality;
	}

	public void setMonsterQuality(Integer monsterQuality) {
		this.monsterQuality = monsterQuality;
	}

	@Column(name = "first_drop", nullable = false)
	public Integer getFirstDrop() {
		return this.firstDrop;
	}

	public void setFirstDrop(Integer firstDrop) {
		this.firstDrop = firstDrop;
	}

}