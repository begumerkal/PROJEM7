package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapCopyTrial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_copy_trial", catalog = "game3")
public class Base1MapCopyTrial implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer type;
	private Integer showId;
	private String sceneId;
	private String sceneIdSunday;
	private Integer openCondition;
	private Integer CUserLv;
	private Integer CEnergyA;
	private Integer CEnergyB;
	private Integer CGold;
	private Integer OPassCondition;
	private Integer ONum;
	private Integer OTime;
	private String AGold;
	private Integer AExperience;
	private Integer corpsExp;
	private String starType;
	private Integer mapLv;
	private Integer clearanceTimes;
	private String showMonster;
	private Integer status;
	private String descript;
	private String goods;
	private Integer monsterLv;
	private Integer monsterStar;
	private Integer monsterQuality;

	// Constructors

	/** default constructor */
	public Base1MapCopyTrial() {
	}

	/** full constructor */
	public Base1MapCopyTrial(Integer id, String name, Integer type,
			Integer showId, String sceneId, String sceneIdSunday,
			Integer openCondition, Integer CUserLv, Integer CEnergyA,
			Integer CEnergyB, Integer CGold, Integer OPassCondition,
			Integer ONum, Integer OTime, String AGold, Integer AExperience,
			Integer corpsExp, String starType, Integer mapLv,
			Integer clearanceTimes, String showMonster, Integer status,
			String descript, String goods, Integer monsterLv,
			Integer monsterStar, Integer monsterQuality) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.showId = showId;
		this.sceneId = sceneId;
		this.sceneIdSunday = sceneIdSunday;
		this.openCondition = openCondition;
		this.CUserLv = CUserLv;
		this.CEnergyA = CEnergyA;
		this.CEnergyB = CEnergyB;
		this.CGold = CGold;
		this.OPassCondition = OPassCondition;
		this.ONum = ONum;
		this.OTime = OTime;
		this.AGold = AGold;
		this.AExperience = AExperience;
		this.corpsExp = corpsExp;
		this.starType = starType;
		this.mapLv = mapLv;
		this.clearanceTimes = clearanceTimes;
		this.showMonster = showMonster;
		this.status = status;
		this.descript = descript;
		this.goods = goods;
		this.monsterLv = monsterLv;
		this.monsterStar = monsterStar;
		this.monsterQuality = monsterQuality;
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

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "show_id", nullable = false)
	public Integer getShowId() {
		return this.showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	@Column(name = "scene_id", nullable = false, length = 125)
	public String getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@Column(name = "scene_id_sunday", nullable = false, length = 125)
	public String getSceneIdSunday() {
		return this.sceneIdSunday;
	}

	public void setSceneIdSunday(String sceneIdSunday) {
		this.sceneIdSunday = sceneIdSunday;
	}

	@Column(name = "open_condition", nullable = false)
	public Integer getOpenCondition() {
		return this.openCondition;
	}

	public void setOpenCondition(Integer openCondition) {
		this.openCondition = openCondition;
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

	@Column(name = "o_num", nullable = false)
	public Integer getONum() {
		return this.ONum;
	}

	public void setONum(Integer ONum) {
		this.ONum = ONum;
	}

	@Column(name = "o_time", nullable = false)
	public Integer getOTime() {
		return this.OTime;
	}

	public void setOTime(Integer OTime) {
		this.OTime = OTime;
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

	@Column(name = "corps_exp", nullable = false)
	public Integer getCorpsExp() {
		return this.corpsExp;
	}

	public void setCorpsExp(Integer corpsExp) {
		this.corpsExp = corpsExp;
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

	@Column(name = "clearance_times", nullable = false)
	public Integer getClearanceTimes() {
		return this.clearanceTimes;
	}

	public void setClearanceTimes(Integer clearanceTimes) {
		this.clearanceTimes = clearanceTimes;
	}

	@Column(name = "show_monster", nullable = false, length = 65535)
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

	@Column(name = "goods", nullable = false, length = 125)
	public String getGoods() {
		return this.goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
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

}