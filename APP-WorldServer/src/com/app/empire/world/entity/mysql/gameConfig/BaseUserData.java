package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseUserData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_user_data", catalog = "game3")
public class BaseUserData implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer baseUserId;
	private Integer userType;
	private Integer internalUser;
	private Integer regE;
	private String nickname;
	private Integer userLv;
	private Integer experience;
	private Integer energy;
	private Integer heroTop;
	private Integer mapId;
	private String mapXy;
	private Integer mapIsCopy;
	private Integer skillPoint;
	private Integer leagueId;
	private Integer rankSportsA;
	private Integer rankSportsB;
	private Integer rankSportsC;
	private Integer rankHigSports;
	private Integer rankWar;
	private Short packGrade;
	private Integer depotGrade;
	private Integer vipLv;
	private Integer money;
	private Integer gold;
	private Integer diamond;
	private Integer force;
	private Integer everyAct;
	private Boolean status;
	private Boolean online;
	private Integer loginSum;
	private Integer seal;
	private Integer serverIp;
	private Integer ontime;
	private Integer downtime;
	private Integer createtime;

	// Constructors

	/** default constructor */
	public BaseUserData() {
	}

	/** minimal constructor */
	public BaseUserData(Integer id, Integer baseUserId, Integer userType,
			Integer internalUser, Integer regE, String nickname,
			Integer userLv, Integer experience, Integer energy,
			Integer heroTop, String mapXy, Integer mapIsCopy,
			Integer skillPoint, Integer rankSportsA, Integer rankSportsB,
			Integer rankSportsC, Integer rankHigSports, Integer rankWar,
			Short packGrade, Integer depotGrade, Integer vipLv, Integer money,
			Integer force, Integer everyAct, Boolean online, Integer loginSum,
			Integer seal, Integer serverIp, Integer ontime, Integer downtime,
			Integer createtime) {
		this.id = id;
		this.baseUserId = baseUserId;
		this.userType = userType;
		this.internalUser = internalUser;
		this.regE = regE;
		this.nickname = nickname;
		this.userLv = userLv;
		this.experience = experience;
		this.energy = energy;
		this.heroTop = heroTop;
		this.mapXy = mapXy;
		this.mapIsCopy = mapIsCopy;
		this.skillPoint = skillPoint;
		this.rankSportsA = rankSportsA;
		this.rankSportsB = rankSportsB;
		this.rankSportsC = rankSportsC;
		this.rankHigSports = rankHigSports;
		this.rankWar = rankWar;
		this.packGrade = packGrade;
		this.depotGrade = depotGrade;
		this.vipLv = vipLv;
		this.money = money;
		this.force = force;
		this.everyAct = everyAct;
		this.online = online;
		this.loginSum = loginSum;
		this.seal = seal;
		this.serverIp = serverIp;
		this.ontime = ontime;
		this.downtime = downtime;
		this.createtime = createtime;
	}

	/** full constructor */
	public BaseUserData(Integer id, Integer baseUserId, Integer userType,
			Integer internalUser, Integer regE, String nickname,
			Integer userLv, Integer experience, Integer energy,
			Integer heroTop, Integer mapId, String mapXy, Integer mapIsCopy,
			Integer skillPoint, Integer leagueId, Integer rankSportsA,
			Integer rankSportsB, Integer rankSportsC, Integer rankHigSports,
			Integer rankWar, Short packGrade, Integer depotGrade,
			Integer vipLv, Integer money, Integer gold, Integer diamond,
			Integer force, Integer everyAct, Boolean status, Boolean online,
			Integer loginSum, Integer seal, Integer serverIp, Integer ontime,
			Integer downtime, Integer createtime) {
		this.id = id;
		this.baseUserId = baseUserId;
		this.userType = userType;
		this.internalUser = internalUser;
		this.regE = regE;
		this.nickname = nickname;
		this.userLv = userLv;
		this.experience = experience;
		this.energy = energy;
		this.heroTop = heroTop;
		this.mapId = mapId;
		this.mapXy = mapXy;
		this.mapIsCopy = mapIsCopy;
		this.skillPoint = skillPoint;
		this.leagueId = leagueId;
		this.rankSportsA = rankSportsA;
		this.rankSportsB = rankSportsB;
		this.rankSportsC = rankSportsC;
		this.rankHigSports = rankHigSports;
		this.rankWar = rankWar;
		this.packGrade = packGrade;
		this.depotGrade = depotGrade;
		this.vipLv = vipLv;
		this.money = money;
		this.gold = gold;
		this.diamond = diamond;
		this.force = force;
		this.everyAct = everyAct;
		this.status = status;
		this.online = online;
		this.loginSum = loginSum;
		this.seal = seal;
		this.serverIp = serverIp;
		this.ontime = ontime;
		this.downtime = downtime;
		this.createtime = createtime;
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

	@Column(name = "base_user_id", nullable = false)
	public Integer getBaseUserId() {
		return this.baseUserId;
	}

	public void setBaseUserId(Integer baseUserId) {
		this.baseUserId = baseUserId;
	}

	@Column(name = "user_type", nullable = false)
	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "internal_user", nullable = false)
	public Integer getInternalUser() {
		return this.internalUser;
	}

	public void setInternalUser(Integer internalUser) {
		this.internalUser = internalUser;
	}

	@Column(name = "reg_e", nullable = false)
	public Integer getRegE() {
		return this.regE;
	}

	public void setRegE(Integer regE) {
		this.regE = regE;
	}

	@Column(name = "nickname", nullable = false, length = 36)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "user_lv", nullable = false)
	public Integer getUserLv() {
		return this.userLv;
	}

	public void setUserLv(Integer userLv) {
		this.userLv = userLv;
	}

	@Column(name = "experience", nullable = false)
	public Integer getExperience() {
		return this.experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@Column(name = "energy", nullable = false)
	public Integer getEnergy() {
		return this.energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	@Column(name = "hero_top", nullable = false)
	public Integer getHeroTop() {
		return this.heroTop;
	}

	public void setHeroTop(Integer heroTop) {
		this.heroTop = heroTop;
	}

	@Column(name = "map_id")
	public Integer getMapId() {
		return this.mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	@Column(name = "map_xy", nullable = false, length = 10)
	public String getMapXy() {
		return this.mapXy;
	}

	public void setMapXy(String mapXy) {
		this.mapXy = mapXy;
	}

	@Column(name = "map_is_copy", nullable = false)
	public Integer getMapIsCopy() {
		return this.mapIsCopy;
	}

	public void setMapIsCopy(Integer mapIsCopy) {
		this.mapIsCopy = mapIsCopy;
	}

	@Column(name = "skill_point", nullable = false)
	public Integer getSkillPoint() {
		return this.skillPoint;
	}

	public void setSkillPoint(Integer skillPoint) {
		this.skillPoint = skillPoint;
	}

	@Column(name = "league_id")
	public Integer getLeagueId() {
		return this.leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

	@Column(name = "rank_sports_a", nullable = false)
	public Integer getRankSportsA() {
		return this.rankSportsA;
	}

	public void setRankSportsA(Integer rankSportsA) {
		this.rankSportsA = rankSportsA;
	}

	@Column(name = "rank_sports_b", nullable = false)
	public Integer getRankSportsB() {
		return this.rankSportsB;
	}

	public void setRankSportsB(Integer rankSportsB) {
		this.rankSportsB = rankSportsB;
	}

	@Column(name = "rank_sports_c", nullable = false)
	public Integer getRankSportsC() {
		return this.rankSportsC;
	}

	public void setRankSportsC(Integer rankSportsC) {
		this.rankSportsC = rankSportsC;
	}

	@Column(name = "rank_hig_sports", nullable = false)
	public Integer getRankHigSports() {
		return this.rankHigSports;
	}

	public void setRankHigSports(Integer rankHigSports) {
		this.rankHigSports = rankHigSports;
	}

	@Column(name = "rank_war", nullable = false)
	public Integer getRankWar() {
		return this.rankWar;
	}

	public void setRankWar(Integer rankWar) {
		this.rankWar = rankWar;
	}

	@Column(name = "pack_grade", nullable = false)
	public Short getPackGrade() {
		return this.packGrade;
	}

	public void setPackGrade(Short packGrade) {
		this.packGrade = packGrade;
	}

	@Column(name = "depot_grade", nullable = false)
	public Integer getDepotGrade() {
		return this.depotGrade;
	}

	public void setDepotGrade(Integer depotGrade) {
		this.depotGrade = depotGrade;
	}

	@Column(name = "vip_lv", nullable = false)
	public Integer getVipLv() {
		return this.vipLv;
	}

	public void setVipLv(Integer vipLv) {
		this.vipLv = vipLv;
	}

	@Column(name = "money", nullable = false)
	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Column(name = "gold")
	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Column(name = "diamond")
	public Integer getDiamond() {
		return this.diamond;
	}

	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
	}

	@Column(name = "force", nullable = false)
	public Integer getForce() {
		return this.force;
	}

	public void setForce(Integer force) {
		this.force = force;
	}

	@Column(name = "every_act", nullable = false)
	public Integer getEveryAct() {
		return this.everyAct;
	}

	public void setEveryAct(Integer everyAct) {
		this.everyAct = everyAct;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "online", nullable = false)
	public Boolean getOnline() {
		return this.online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	@Column(name = "login_sum", nullable = false)
	public Integer getLoginSum() {
		return this.loginSum;
	}

	public void setLoginSum(Integer loginSum) {
		this.loginSum = loginSum;
	}

	@Column(name = "seal", nullable = false)
	public Integer getSeal() {
		return this.seal;
	}

	public void setSeal(Integer seal) {
		this.seal = seal;
	}

	@Column(name = "server_ip", nullable = false)
	public Integer getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(Integer serverIp) {
		this.serverIp = serverIp;
	}

	@Column(name = "ontime", nullable = false)
	public Integer getOntime() {
		return this.ontime;
	}

	public void setOntime(Integer ontime) {
		this.ontime = ontime;
	}

	@Column(name = "downtime", nullable = false)
	public Integer getDowntime() {
		return this.downtime;
	}

	public void setDowntime(Integer downtime) {
		this.downtime = downtime;
	}

	@Column(name = "createtime", nullable = false)
	public Integer getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}

}