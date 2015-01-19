package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the tab_player_info database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_player_info")
public class PlayerInfo implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 玩家附属信息id
	private int activity; // 玩家活跃度
	private String activeRewards; // 玩家活跃度奖励领取记录
	private List<Integer> activeRewardList = null;
	private int nearbyId; // 附近好友信息
	private String rechargeCritValue; // 充值暴击值
	private int injuryFree; // 星魂加成免伤
	private int wreckDefense; // 星魂加成破防
	private int reduceCrit; // 星魂加成免暴
	private int reduceBury; // 星魂加成免坑
	private int force; // 星魂加成力量
	private int armor; // 星魂加成护甲
	private int agility; // 星魂加成敏捷
	private int physique; // 星魂加成体质
	private int luck; // 星魂加成幸运
	private int attack; // 星魂加成攻击力
	private int defend; // 星魂加成防御力
	private int defense; // 星魂加成暴击率（1000倍）
	private int hp; // 星魂加成血量
	private int starSoulLeve; // 星魂等级
	private int soulDot; // 当前星点
	private int practiceInjuryFree; // 修炼加成免伤
	private int practiceWreckDefense; // 修炼加成破防
	private int practiceReduceCrit; // 修炼加成免暴
	private int practiceReduceBury; // 修炼加成免坑
	private int practiceForce; // 修炼加成力量
	private int practiceArmor; // 修炼加成护甲
	private int practiceAgility; // 修炼加成敏捷
	private int practicePhysique; // 修炼加成体质
	private int practiceLuck; // 修炼加成幸运
	private int practiceAttack; // 修炼加成攻击力
	private int practiceDefend; // 修炼加成防御力
	private int practiceDefense; // 修炼加成暴击率（1000倍）
	private int practiceHp; // 修炼加成血量
	private int practiceLeve; // 修炼当前等级
	private String practiceAttributeExp; // 属性经验值,分割
	private String expFullAttribute; // 满经验值的属性，分割
	private int useTodayNumber; // 今日还可以使用勋章数
	private Date lastPracticeTime; // 最后修炼时间
	private int everyDayFirstChargeNum;// 玩家可以领取的每日首充次数
	private String zflhRecord; // 祝福礼盒的记录（已领取）
	private int monthCardId; // 购买的月卡id
	private Date buyMonthCardTime; // 购买月卡时间
	private int remainingRemindNum; // 过期提醒余下次数
	private Date lastReceiveMonthCardRebateTime;// 最后领取月卡返利时间
	private int vigor; // 活力值
	private Date vigorUpdateTime; // 活力值自动更新时间（用于离线后再登陆时补发活力值）
	private Date zfspUpdateTime; // 祝福碎片更新时间

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
	@Column(name = "activity", precision = 10)
	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	@Basic()
	@Column(name = "active_rewards", length = 255)
	public String getActiveRewards() {
		return null == activeRewards ? "" : activeRewards;
	}

	public void setActiveRewards(String activeRewards) {
		this.activeRewards = activeRewards;
	}

	@Basic()
	@Column(name = "nearby_id", precision = 10)
	public int getNearbyId() {
		return nearbyId;
	}

	public void setNearbyId(int nearbyId) {
		this.nearbyId = nearbyId;
	}

	@Basic()
	@Column(name = "recharge_crit_value", length = 255)
	public String getRechargeCritValue() {
		return rechargeCritValue;
	}

	public void setRechargeCritValue(String rechargeCritValue) {
		this.rechargeCritValue = rechargeCritValue;
	}

	@Basic()
	@Column(name = "defend")
	public int getDefend() {
		return this.defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	@Basic()
	@Column(name = "defense")
	public int getDefense() {
		return this.defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	@Basic()
	@Column(name = "injury_free")
	public int getInjuryFree() {
		return injuryFree;
	}

	public void setInjuryFree(int injuryFree) {
		this.injuryFree = injuryFree;
	}

	@Basic()
	@Column(name = "wreck_defense")
	public int getWreckDefense() {
		return wreckDefense;
	}

	public void setWreckDefense(int wreckDefense) {
		this.wreckDefense = wreckDefense;
	}

	@Basic()
	@Column(name = "reduce_crit")
	public int getReduceCrit() {
		return reduceCrit;
	}

	public void setReduceCrit(int reduceCrit) {
		this.reduceCrit = reduceCrit;
	}

	@Basic()
	@Column(name = "reduce_bury")
	public int getReduceBury() {
		return reduceBury;
	}

	public void setReduceBury(int reduceBury) {
		this.reduceBury = reduceBury;
	}

	@Basic()
	@Column(name = "p_force")
	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	@Basic()
	@Column(name = "armor")
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	@Basic()
	@Column(name = "agility")
	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	@Basic()
	@Column(name = "physique")
	public int getPhysique() {
		return physique;
	}

	public void setPhysique(int physique) {
		this.physique = physique;
	}

	@Basic()
	@Column(name = "luck")
	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	@Basic()
	@Column(name = "attack")
	public int getAttack() {
		return this.attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	@Basic()
	@Column(name = "star_soul_leve", length = 3)
	public int getStarSoulLeve() {
		return starSoulLeve;
	}

	public void setStarSoulLeve(int starSoulLeve) {
		this.starSoulLeve = starSoulLeve;
	}

	@Basic()
	@Column(name = "soul_dot", length = 2)
	public int getSoulDot() {
		return soulDot;
	}

	public void setSoulDot(int soulDot) {
		this.soulDot = soulDot;
	}

	@Basic()
	@Column(name = "hp")
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	@Basic()
	@Column(name = "practice_injury_free")
	public int getPracticeInjuryFree() {
		return practiceInjuryFree;
	}

	public void setPracticeInjuryFree(int practiceInjuryFree) {
		this.practiceInjuryFree = practiceInjuryFree;
	}

	@Basic()
	@Column(name = "practice_wreck_defense")
	public int getPracticeWreckDefense() {
		return practiceWreckDefense;
	}

	public void setPracticeWreckDefense(int practiceWreckDefense) {
		this.practiceWreckDefense = practiceWreckDefense;
	}

	@Basic()
	@Column(name = "practice_reduce_crit")
	public int getPracticeReduceCrit() {
		return practiceReduceCrit;
	}

	public void setPracticeReduceCrit(int practiceReduceCrit) {
		this.practiceReduceCrit = practiceReduceCrit;
	}

	@Basic()
	@Column(name = "practice_reduce_bury")
	public int getPracticeReduceBury() {
		return practiceReduceBury;
	}

	public void setPracticeReduceBury(int practiceReduceBury) {
		this.practiceReduceBury = practiceReduceBury;
	}

	@Basic()
	@Column(name = "practice_force")
	public int getPracticeForce() {
		return practiceForce;
	}

	public void setPracticeForce(int practiceForce) {
		this.practiceForce = practiceForce;
	}

	@Basic()
	@Column(name = "practice_armor")
	public int getPracticeArmor() {
		return practiceArmor;
	}

	public void setPracticeArmor(int practiceArmor) {
		this.practiceArmor = practiceArmor;
	}

	@Basic()
	@Column(name = "practice_agility")
	public int getPracticeAgility() {
		return practiceAgility;
	}

	public void setPracticeAgility(int practiceAgility) {
		this.practiceAgility = practiceAgility;
	}

	@Basic()
	@Column(name = "practice_physique")
	public int getPracticePhysique() {
		return practicePhysique;
	}

	public void setPracticePhysique(int practicePhysique) {
		this.practicePhysique = practicePhysique;
	}

	@Basic()
	@Column(name = "practice_luck")
	public int getPracticeLuck() {
		return practiceLuck;
	}

	public void setPracticeLuck(int practiceLuck) {
		this.practiceLuck = practiceLuck;
	}

	@Basic()
	@Column(name = "practice_attack")
	public int getPracticeAttack() {
		return practiceAttack;
	}

	public void setPracticeAttack(int practiceAttack) {
		this.practiceAttack = practiceAttack;
	}

	@Basic()
	@Column(name = "practice_defend")
	public int getPracticeDefend() {
		return practiceDefend;
	}

	public void setPracticeDefend(int practiceDefend) {
		this.practiceDefend = practiceDefend;
	}

	@Basic()
	@Column(name = "practice_defense")
	public int getPracticeDefense() {
		return practiceDefense;
	}

	public void setPracticeDefense(int practiceDefense) {
		this.practiceDefense = practiceDefense;
	}

	@Basic()
	@Column(name = "practice_hp")
	public int getPracticeHp() {
		return practiceHp;
	}

	public void setPracticeHp(int practiceHp) {
		this.practiceHp = practiceHp;
	}

	@Basic()
	@Column(name = "practice_leve", length = 3)
	public int getPracticeLeve() {
		return practiceLeve;
	}

	public void setPracticeLeve(int practiceLeve) {
		this.practiceLeve = practiceLeve;
	}

	@Basic()
	@Column(name = "practice_attribute_exp", length = 100)
	public String getPracticeAttributeExp() {
		return practiceAttributeExp;
	}

	public void setPracticeAttributeExp(String practiceAttributeExp) {
		this.practiceAttributeExp = practiceAttributeExp;
	}

	@Basic()
	@Column(name = "exp_full_attribute", length = 150)
	public String getExpFullAttribute() {
		return expFullAttribute;
	}

	public void setExpFullAttribute(String expFullAttribute) {
		this.expFullAttribute = expFullAttribute;
	}

	@Basic()
	@Column(name = "use_today_number")
	public int getUseTodayNumber() {
		return useTodayNumber;
	}

	public void setUseTodayNumber(int useTodayNumber) {
		this.useTodayNumber = useTodayNumber;
	}

	@Basic()
	@Column(name = "every_day_first_charge_num")
	public int getEveryDayFirstChargeNum() {
		return everyDayFirstChargeNum;
	}

	public void setEveryDayFirstChargeNum(int everyDayFirstChargeNum) {
		this.everyDayFirstChargeNum = everyDayFirstChargeNum;
	}

	// @Basic()
	// @Column(name = "practice_status")
	// public boolean getPracticeStatus() {
	// return practiceStatus;
	// }
	//
	// public void setPracticeStatus(boolean practiceStatus) {
	// this.practiceStatus = practiceStatus;
	// }

	@Basic()
	@Column(name = "last_practice_time")
	public Date getLastPracticeTime() {
		return lastPracticeTime;
	}

	public void setLastPracticeTime(Date lastPracticeTime) {
		this.lastPracticeTime = lastPracticeTime;
	}

	@Basic()
	@Column(name = "zflh_record")
	public String getZflhRecord() {
		return zflhRecord;
	}

	public void setZflhRecord(String zflhRecord) {
		this.zflhRecord = zflhRecord;
	}

	@Basic()
	@Column(name = "month_card_id")
	public int getMonthCardId() {
		return monthCardId;
	}

	public void setMonthCardId(int monthCardId) {
		this.monthCardId = monthCardId;
	}

	@Basic()
	@Column(name = "buy_month_card_time")
	public Date getBuyMonthCardTime() {
		return buyMonthCardTime;
	}

	public void setBuyMonthCardTime(Date buyMonthCardTime) {
		this.buyMonthCardTime = buyMonthCardTime;
	}

	@Basic()
	@Column(name = "remaining_remind_num", length = 2)
	public int getRemainingRemindNum() {
		return remainingRemindNum;
	}

	public void setRemainingRemindNum(int remainingRemindNum) {
		this.remainingRemindNum = remainingRemindNum;
	}

	@Basic()
	@Column(name = "last_receive_month_card_rebate_time")
	public Date getLastReceiveMonthCardRebateTime() {
		return lastReceiveMonthCardRebateTime;
	}

	public void setLastReceiveMonthCardRebateTime(Date lastReceiveMonthCardRebateTime) {
		this.lastReceiveMonthCardRebateTime = lastReceiveMonthCardRebateTime;
	}

	@Basic()
	@Column(name = "vigor")
	public int getVigor() {
		return vigor;
	}

	public void setVigor(int vigor) {
		this.vigor = vigor;
	}

	@Basic()
	@Column(name = "vigor_updatetime")
	public Date getVigorUpdateTime() {
		return vigorUpdateTime;
	}

	@Basic()
	@Column(name = "zfsp_updatetime")
	public Date getZfspUpdateTime() {
		return zfspUpdateTime;
	}

	public void setZfspUpdateTime(Date zfspUpdateTime) {
		this.zfspUpdateTime = zfspUpdateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setVigorUpdateTime(Date vigorUpdateTime) {
		this.vigorUpdateTime = vigorUpdateTime;
	}

	/**
	 * 更新玩家的活跃度奖励领取进度
	 * 
	 * @param rewardIndex
	 */
	@Transient
	public void updateActiveRewards(Integer rewardIndex) {
		String activeRewards = getActiveRewards();
		if (activeRewards.length() > 0) {
			activeRewards += ",";
		}
		activeRewards += rewardIndex;
		setActiveRewards(activeRewards);
		initActiveRewardList();
	}

	@Transient
	public void initActiveRewardList() {
		List<Integer> activeRewardList = new ArrayList<Integer>();
		if (activeRewards.length() > 0) {
			for (String reward : activeRewards.split(",")) {
				activeRewardList.add(Integer.parseInt(reward));
			}
		}
		this.activeRewardList = activeRewardList;
	}

	/**
	 * 判断某个活跃度奖励是否已领取
	 * 
	 * @param rewardIndex
	 * @return
	 */
	@Transient
	public boolean isActiveReward(Integer rewardIndex) {
		if (null == activeRewardList)
			initActiveRewardList();
		return activeRewardList.contains(rewardIndex);
	}
}