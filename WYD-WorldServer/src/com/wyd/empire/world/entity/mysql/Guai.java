package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_guai")
public class Guai implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 怪物ID
	private int dungeon; // 副本难度
	private int guaiId; // 怪物ID
	private String name; // 名字
	private Integer sex; // 0:男 1:女
	private String suit_head; // 着装头(如果type==1时,值为stand)
	private String suit_face; // 着装脸(如果type==1时,值为stand)
	private String suit_body; // 着装身(如果type==1时,值为stand)
	private String suit_weapon; // 着装武器(如果type==1时,值为stand)
	private Integer weapon_type; // 武器类型
	private int type; // 0:有着装的小怪 1:没有着装的小怪 2:boss
	private Integer attack_type; // 0:会远近攻 1:只会远攻 2:只会近攻 3:不会攻击不会移动
									// (type==2时,即大boss一定是0)
	private Integer level; // 等级
	private Integer hp; // 生命值
	private Integer sp; // 怒气值
	private Integer pf; // 体力
	private Integer defend; // 防御力
	private Integer attack; // 攻击力
	private Integer attackArea; // 攻击范围
	private Integer criticalRate; // 万份比数值(放大一万陪) 增加暴击率
	private Integer bigSkillType; // 大招类型.没有大招:-1
	private String explode; // 爆炸图(type==0时,这里不需要,直接根武器相关)
	private String broken; // 坑图(type==0时,这里不需要,直接根武器相关)
	private String aniFileId; // 动画文件id.格式:[boss[id]]或[guai[id]],如:boss1,guai1
	private Integer could_build_guai; // 是否可以招唤小怪
	private String build_guai_id; // 放出的小怪的id
	private int injuryFree; // 免伤
	private int wreckDefense; // 破防
	private int reduceCrit; // 免暴
	private int reduceBury; // 免坑
	private int force; // 力量
	private int armor; // 护甲
	private int agility; // 敏捷
	private int physique; // 体质
	private int luck; // 幸运
	private int skill_1; // 技能1伤害
	private int skill_2; // 技能2伤害
	private int skill_3; // 技能3伤害
	private int skill_4; // 技能4伤害
	private int skill_5; // 技能5伤害
	private String guai_ai; // 怪的Ai控制
	private String dialogue; // 怪的对话文本

	public Guai() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "dungeon", precision = 10)
	public int getDungeon() {
		return dungeon;
	}

	public void setDungeon(int dungeon) {
		this.dungeon = dungeon;
	}

	@Basic()
	@Column(name = "guai_id", precision = 10)
	public int getGuaiId() {
		return guaiId;
	}

	public void setGuaiId(int guaiId) {
		this.guaiId = guaiId;
	}

	@Basic()
	@Column(name = "suit_head", length = 16)
	public String getSuit_head() {
		return suit_head;
	}

	public void setSuit_head(String suitHead) {
		suit_head = suitHead;
	}

	@Basic()
	@Column(name = "suit_face", length = 16)
	public String getSuit_face() {
		return suit_face;
	}

	public void setSuit_face(String suitFace) {
		suit_face = suitFace;
	}

	@Basic()
	@Column(name = "suit_body", length = 16)
	public String getSuit_body() {
		return suit_body;
	}

	public void setSuit_body(String suitBody) {
		suit_body = suitBody;
	}

	@Basic()
	@Column(name = "suit_weapon", length = 16)
	public String getSuit_weapon() {
		return suit_weapon;
	}

	public void setSuit_weapon(String suitWeapon) {
		suit_weapon = suitWeapon;
	}

	@Basic()
	@Column(name = "type", precision = 10)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "could_build_guai", precision = 10)
	public Integer getCould_build_guai() {
		return could_build_guai;
	}

	public void setCould_build_guai(Integer couldBuildGuai) {
		could_build_guai = couldBuildGuai;
	}

	@Basic()
	@Column(name = "name", length = 16)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "sex", precision = 10)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Basic()
	@Column(name = "weapon_type", precision = 10)
	public Integer getWeapon_type() {
		return weapon_type;
	}

	public void setWeapon_type(Integer weaponType) {
		weapon_type = weaponType;
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "hp", precision = 10)
	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	@Basic()
	@Column(name = "sp", precision = 10)
	public Integer getSp() {
		return sp;
	}

	public void setSp(Integer sp) {
		this.sp = sp;
	}

	@Basic()
	@Column(name = "pf", precision = 10)
	public Integer getPf() {
		return pf;
	}

	public void setPf(Integer pf) {
		this.pf = pf;
	}

	@Basic()
	@Column(name = "defend", precision = 10)
	public Integer getDefend() {
		return defend;
	}

	public void setDefend(Integer defend) {
		this.defend = defend;
	}

	@Basic()
	@Column(name = "attack", precision = 10)
	public Integer getAttack() {
		return attack;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}

	@Basic()
	@Column(name = "attackArea", precision = 10)
	public Integer getAttackArea() {
		return attackArea;
	}

	public void setAttackArea(Integer attackArea) {
		this.attackArea = attackArea;
	}

	@Basic()
	@Column(name = "criticalRate", precision = 10)
	public Integer getCriticalRate() {
		return criticalRate;
	}

	public void setCriticalRate(Integer criticalRate) {
		this.criticalRate = criticalRate;
	}

	@Basic()
	@Column(name = "bigSkillType", precision = 10)
	public Integer getBigSkillType() {
		return bigSkillType;
	}

	public void setBigSkillType(Integer bigSkillType) {
		this.bigSkillType = bigSkillType;
	}

	@Basic()
	@Column(name = "explode", length = 16)
	public String getExplode() {
		return explode;
	}

	public void setExplode(String explode) {
		this.explode = explode;
	}

	@Basic()
	@Column(name = "broken", length = 16)
	public String getBroken() {
		return broken;
	}

	public void setBroken(String broken) {
		this.broken = broken;
	}

	@Basic()
	@Column(name = "AniFileId", length = 16)
	public String getAniFileId() {
		return aniFileId;
	}

	public void setAniFileId(String aniFileId) {
		this.aniFileId = aniFileId;
	}

	@Basic()
	@Column(name = "build_guai_id", length = 200)
	public String getBuild_guai_id() {
		return build_guai_id;
	}

	public void setBuild_guai_id(String buildGuaiId) {
		build_guai_id = buildGuaiId;
	}

	@Basic()
	@Column(name = "attack_type", precision = 10)
	public Integer getAttack_type() {
		return attack_type;
	}

	public void setAttack_type(Integer attackType) {
		attack_type = attackType;
	}

	@Basic()
	@Column(name = "injury_free", precision = 10)
	public int getInjuryFree() {
		return injuryFree;
	}

	public void setInjuryFree(int injuryFree) {
		this.injuryFree = injuryFree;
	}

	@Basic()
	@Column(name = "wreck_defense", precision = 10)
	public int getWreckDefense() {
		return wreckDefense;
	}

	public void setWreckDefense(int wreckDefense) {
		this.wreckDefense = wreckDefense;
	}

	@Basic()
	@Column(name = "reduce_crit", precision = 10)
	public int getReduceCrit() {
		return reduceCrit;
	}

	public void setReduceCrit(int reduceCrit) {
		this.reduceCrit = reduceCrit;
	}

	@Basic()
	@Column(name = "reduce_bury", precision = 10)
	public int getReduceBury() {
		return reduceBury;
	}

	public void setReduceBury(int reduceBury) {
		this.reduceBury = reduceBury;
	}

	@Basic()
	@Column(name = "p_force", precision = 10)
	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	@Basic()
	@Column(name = "armor", precision = 10)
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	@Basic()
	@Column(name = "agility", precision = 10)
	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	@Basic()
	@Column(name = "physique", precision = 10)
	public int getPhysique() {
		return physique;
	}

	public void setPhysique(int physique) {
		this.physique = physique;
	}

	@Basic()
	@Column(name = "luck", precision = 10)
	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	@Basic()
	@Column(name = "skill_1", precision = 10)
	public int getSkill_1() {
		return skill_1;
	}

	public void setSkill_1(int skill_1) {
		this.skill_1 = skill_1;
	}

	@Basic()
	@Column(name = "skill_2", precision = 10)
	public int getSkill_2() {
		return skill_2;
	}

	public void setSkill_2(int skill_2) {
		this.skill_2 = skill_2;
	}

	@Basic()
	@Column(name = "skill_3", precision = 10)
	public int getSkill_3() {
		return skill_3;
	}

	public void setSkill_3(int skill_3) {
		this.skill_3 = skill_3;
	}

	@Basic()
	@Column(name = "skill_4", precision = 10)
	public int getSkill_4() {
		return skill_4;
	}

	public void setSkill_4(int skill_4) {
		this.skill_4 = skill_4;
	}

	@Basic()
	@Column(name = "skill_5", precision = 10)
	public int getSkill_5() {
		return skill_5;
	}

	public void setSkill_5(int skill_5) {
		this.skill_5 = skill_5;
	}

	@Basic()
	@Column(name = "guai_ai", length = 255)
	public String getGuai_ai() {
		return guai_ai;
	}

	public void setGuai_ai(String guai_ai) {
		this.guai_ai = guai_ai;
	}

	@Basic()
	@Column(name = "dialogue", length = 255)
	public String getDialogue() {
		return dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}
}