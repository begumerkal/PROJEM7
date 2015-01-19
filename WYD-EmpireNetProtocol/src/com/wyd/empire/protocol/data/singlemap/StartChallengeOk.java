package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class StartChallengeOk extends AbstractData {
	private int battleId;              // 战斗组Id
	private String bossMapName;        // 副本名称
	private String battleMap;          // 战斗地图资源名称
	private String cleanCondition;     // 过关条件
	private String suit_head;          // 着装串头
	private String suit_face;          // 着装串脸
	private String suit_body;          // 着装串身
	private String suit_weapon;        // 着装串武器
	private String suit_wing;          // 着装翅膀
	private String player_title;       // 称号
	private String player_community;   // 公会名称
	private int weapon_type;           // 武器类型 0:投掷类 1:射击类
	private int maxHP;                 // 最大血量
	private int maxPF;                 // 最大体力值
	private int maxSP;                 // 最大怒气
	private int attack;                // 普攻击力
	private int bigSkillAttack;        // 大招攻击力
	private int crit;                  // 爆击
	private int defence;               // 防御力
	private int bigSkillType;          // 大招类型
	private int explodeRadius;         // 爆破范围
	private int injuryFree;            // 免伤(10000)
	private int wreckDefense;          // 破防(10000)
	private int reduceCrit;            // 免暴(10000)
	private int reduceBury;            // 免坑(10000)
	private int zsleve;                // 玩家转生等级
	private int skillful;              // 玩家武器熟练度
	private int[] item_id;             // 技能道具ID
	private int[] item_used;           // 是否装备道具（1有装备，0没装备，-1锁）
	private String[] item_img;         // 道具图像路径
	private String[] item_name;        // 道具名称
	private String[] item_desc;        // 道具描述
	private int[] item_type;           // 道具类型
	private int[] item_subType;        // 道具子类型
	private int[] item_param1;         // 参数1
	private int[] item_param2;         // 参数2
	private int[] item_ConsumePower;   // 消耗体力
	private int[] specialAttackType;   // 附加的特殊攻击类型
	private int[] specialAttackParam;  // 附加的特殊攻击数值参数
	private int[] weaponSkillPlayerId; // 武器技能属于那一个角色的
	private String[] weaponSkillName;  // 武器技能名称
	private int[] weaponSkillType;     // 武器技能类型
	private int[] weaponSkillChance;   // 武器技能出现概率(0~10000)
	private int[] weaponSkillParam1;   // 武器技能参数1
	private int[] weaponSkillParam2;   // 武器技能参数2
	private int posX;                  // 玩家的x轴坐标
	private int posY;                  // 玩家的y轴坐标
	private String playerName;         // 房间内玩家昵称
	private int    playerLevel;        // 房间内玩家等级
	private int    boyOrGirl;          // 男还是女
	private int[] guai_posX;           // 怪的x轴坐标
	private int[] guai_posY;           // 怪的Y轴坐标
	private int[] guai_id;             // 怪在怪表中的id
	private String[] guai_name;        // 怪的名字
	private int[] guai_sex;            // 怪的性别 0:男 1:女
	private String[] guai_suit_head;   // 怪的着装头(如果type==1时,值为stand)
	private String[] guai_suit_face;   // 怪的着装脸(如果type==1时,值为stand)
	private String[] guai_suit_body;   // 怪的着装身(如果type==1时,值为stand)
	private String[] guai_suit_weapon; // 怪的着装武器(如果type==1时,值为stand)
	private int[] guai_weapon_type;    // 怪的武器类型
	private int[] guai_type;           // 怪的类型0:有着装的小怪 1:没有着装的小怪 2:boss
	private int[] guai_attacktype;     // 怪的攻击类型 0:会远近攻 1:只会远攻 2:只会近攻 3:不会攻击不会移动 (type==2时,即大boss一定是0)
	private int[] guai_level;          // 怪的等级
	private int[] guai_sp;             // 怪的怒气值
	private int[] guai_pf;             // 怪的体力
	private int[] guai_hp	;          // 怪的生命值
	private int[] guai_defend;         // 怪的防御力
	private int[] guai_attack;         // 怪的攻击力
	private int[] guai_physique;       // 怪的体质
	private int[] guai_p_force;        // 怪的力量
	private int[] guai_armor;          // 怪的护甲
	private int[] guai_agility;        // 怪的敏捷
	private int[] guai_luck;           // 怪的幸运
	private int[] guai_injuryFree;     // 怪的免伤(10000)
	private int[] guai_wreckDefense;   // 怪的破防(10000)
	private int[] guai_reduceCrit;     // 怪的免暴(10000)
	private int[] guai_reduceBury;     // 怪的免坑(10000)
	private int[] guai_attackArea;     // 怪的攻击范围
	private int[] guai_criticalRate;   // 怪的暴击率 万份比数值(放大一万陪)
	private String[] guai_AniFileId;   // 动画文件id.格式:[boss[id]]或[guai[id]],如:boss1,guai1
	private String[]guai_ai;           // 怪的Ai控制
	private String[] guai_dialogue;    // 怪的对话文本
	private int[] buffType;            // 玩家公会技能等
	private int[] buffParam;           // 玩家公会技能等
	private int petId;                 // 宠物id，0表示无宠物
	private String petIcon;            // 宠物icon
	private int petType;               // 宠物类型
	private int petSkillId;            // 宠物技能id
	private int petProbability;        // 宠物攻击概率
	private int petParam1;             // 宠物参数1
	private int petParam2;             // 宠物参数2
	private String petEffect;          // 宠物攻击特效名称
	private int    petVersion;         // 宠物版本 1 旧版，2新版
	private int force;				   // 力量
	private int armor;				   // 护甲
	private int	constitution;	       // 体质
    private int	agility;	           // 敏捷
    private int	lucky;     	           // 幸运

	public StartChallengeOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartChallengeOk, sessionId, serial);
    }
	public StartChallengeOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartChallengeOk);
	}
	public int getBattleId() {
		return battleId;
	}
	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}
	public String getBossMapName() {
		return bossMapName;
	}
	public void setBossMapName(String bossMapName) {
		this.bossMapName = bossMapName;
	}
	public String getBattleMap() {
		return battleMap;
	}
	public void setBattleMap(String battleMap) {
		this.battleMap = battleMap;
	}
	public String getCleanCondition() {
		return cleanCondition;
	}
	public void setCleanCondition(String cleanCondition) {
		this.cleanCondition = cleanCondition;
	}
	public String getSuit_head() {
		return suit_head;
	}
	public void setSuit_head(String suit_head) {
		this.suit_head = suit_head;
	}
	public String getSuit_face() {
		return suit_face;
	}
	public void setSuit_face(String suit_face) {
		this.suit_face = suit_face;
	}
	public String getSuit_body() {
		return suit_body;
	}
	public void setSuit_body(String suit_body) {
		this.suit_body = suit_body;
	}
	public String getSuit_weapon() {
		return suit_weapon;
	}
	public void setSuit_weapon(String suit_weapon) {
		this.suit_weapon = suit_weapon;
	}
	
	public String getSuit_wing() {
		return suit_wing;
	}
	public void setSuit_wing(String suit_wing) {
		this.suit_wing = suit_wing;
	}
	public String getPlayer_title() {
		return player_title;
	}
	public void setPlayer_title(String player_title) {
		this.player_title = player_title;
	}
	public String getPlayer_community() {
		return player_community;
	}
	public void setPlayer_community(String player_community) {
		this.player_community = player_community;
	}
	public int getWeapon_type() {
		return weapon_type;
	}
	public void setWeapon_type(int weapon_type) {
		this.weapon_type = weapon_type;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getMaxPF() {
		return maxPF;
	}
	public void setMaxPF(int maxPF) {
		this.maxPF = maxPF;
	}
	public int getMaxSP() {
		return maxSP;
	}
	public void setMaxSP(int maxSP) {
		this.maxSP = maxSP;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getBigSkillAttack() {
		return bigSkillAttack;
	}
	public void setBigSkillAttack(int bigSkillAttack) {
		this.bigSkillAttack = bigSkillAttack;
	}
	
	public int getCrit() {
		return crit;
	}
	public void setCrit(int crit) {
		this.crit = crit;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	public int getBigSkillType() {
		return bigSkillType;
	}
	public void setBigSkillType(int bigSkillType) {
		this.bigSkillType = bigSkillType;
	}
	public int getExplodeRadius() {
		return explodeRadius;
	}
	public void setExplodeRadius(int explodeRadius) {
		this.explodeRadius = explodeRadius;
	}
	public int getInjuryFree() {
		return injuryFree;
	}
	public void setInjuryFree(int injuryFree) {
		this.injuryFree = injuryFree;
	}
	public int getWreckDefense() {
		return wreckDefense;
	}
	public void setWreckDefense(int wreckDefense) {
		this.wreckDefense = wreckDefense;
	}
	public int getReduceCrit() {
		return reduceCrit;
	}
	public void setReduceCrit(int reduceCrit) {
		this.reduceCrit = reduceCrit;
	}
	public int getReduceBury() {
		return reduceBury;
	}
	public void setReduceBury(int reduceBury) {
		this.reduceBury = reduceBury;
	}
	public int getZsleve() {
		return zsleve;
	}
	public void setZsleve(int zsleve) {
		this.zsleve = zsleve;
	}
	public int getSkillful() {
		return skillful;
	}
	public void setSkillful(int skillful) {
		this.skillful = skillful;
	}
	public int[] getItem_id() {
		return item_id;
	}
	public void setItem_id(int[] item_id) {
		this.item_id = item_id;
	}
	public int[] getItem_used() {
		return item_used;
	}
	public void setItem_used(int[] item_used) {
		this.item_used = item_used;
	}
	public String[] getItem_img() {
		return item_img;
	}
	public void setItem_img(String[] item_img) {
		this.item_img = item_img;
	}
	public String[] getItem_name() {
		return item_name;
	}
	public void setItem_name(String[] item_name) {
		this.item_name = item_name;
	}
	public String[] getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String[] item_desc) {
		this.item_desc = item_desc;
	}
	public int[] getItem_type() {
		return item_type;
	}
	public void setItem_type(int[] item_type) {
		this.item_type = item_type;
	}
	public int[] getItem_subType() {
		return item_subType;
	}
	public void setItem_subType(int[] item_subType) {
		this.item_subType = item_subType;
	}
	public int[] getItem_param1() {
		return item_param1;
	}
	public void setItem_param1(int[] item_param1) {
		this.item_param1 = item_param1;
	}
	public int[] getItem_param2() {
		return item_param2;
	}
	public void setItem_param2(int[] item_param2) {
		this.item_param2 = item_param2;
	}
	public int[] getItem_ConsumePower() {
		return item_ConsumePower;
	}
	public void setItem_ConsumePower(int[] item_ConsumePower) {
		this.item_ConsumePower = item_ConsumePower;
	}
	public int[] getWeaponSkillPlayerId() {
		return weaponSkillPlayerId;
	}
	public void setWeaponSkillPlayerId(int[] weaponSkillPlayerId) {
		this.weaponSkillPlayerId = weaponSkillPlayerId;
	}
	public String[] getWeaponSkillName() {
		return weaponSkillName;
	}
	public void setWeaponSkillName(String[] weaponSkillName) {
		this.weaponSkillName = weaponSkillName;
	}
	public int[] getWeaponSkillType() {
		return weaponSkillType;
	}
	public void setWeaponSkillType(int[] weaponSkillType) {
		this.weaponSkillType = weaponSkillType;
	}
	public int[] getWeaponSkillChance() {
		return weaponSkillChance;
	}
	public void setWeaponSkillChance(int[] weaponSkillChance) {
		this.weaponSkillChance = weaponSkillChance;
	}
	public int[] getWeaponSkillParam1() {
		return weaponSkillParam1;
	}
	public void setWeaponSkillParam1(int[] weaponSkillParam1) {
		this.weaponSkillParam1 = weaponSkillParam1;
	}
	public int[] getWeaponSkillParam2() {
		return weaponSkillParam2;
	}
	public void setWeaponSkillParam2(int[] weaponSkillParam2) {
		this.weaponSkillParam2 = weaponSkillParam2;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int[] getGuai_posX() {
		return guai_posX;
	}
	public void setGuai_posX(int[] guai_posX) {
		this.guai_posX = guai_posX;
	}
	public int[] getGuai_posY() {
		return guai_posY;
	}
	public void setGuai_posY(int[] guai_posY) {
		this.guai_posY = guai_posY;
	}
	public int[] getGuai_id() {
		return guai_id;
	}
	public void setGuai_id(int[] guai_id) {
		this.guai_id = guai_id;
	}
	
	
	public String[] getGuai_name() {
		return guai_name;
	}
	public void setGuai_name(String[] guai_name) {
		this.guai_name = guai_name;
	}
	public int[] getGuai_sex() {
		return guai_sex;
	}
	public void setGuai_sex(int[] guai_sex) {
		this.guai_sex = guai_sex;
	}
	public String[] getGuai_suit_head() {
		return guai_suit_head;
	}
	public void setGuai_suit_head(String[] guai_suit_head) {
		this.guai_suit_head = guai_suit_head;
	}
	public String[] getGuai_suit_face() {
		return guai_suit_face;
	}
	public void setGuai_suit_face(String[] guai_suit_face) {
		this.guai_suit_face = guai_suit_face;
	}
	public String[] getGuai_suit_body() {
		return guai_suit_body;
	}
	public void setGuai_suit_body(String[] guai_suit_body) {
		this.guai_suit_body = guai_suit_body;
	}
	public String[] getGuai_suit_weapon() {
		return guai_suit_weapon;
	}
	public void setGuai_suit_weapon(String[] guai_suit_weapon) {
		this.guai_suit_weapon = guai_suit_weapon;
	}
	public int[] getGuai_weapon_type() {
		return guai_weapon_type;
	}
	public void setGuai_weapon_type(int[] guai_weapon_type) {
		this.guai_weapon_type = guai_weapon_type;
	}
	public int[] getGuai_type() {
		return guai_type;
	}
	public void setGuai_type(int[] guai_type) {
		this.guai_type = guai_type;
	}
	public int[] getGuai_attacktype() {
		return guai_attacktype;
	}
	public void setGuai_attacktype(int[] guai_attacktype) {
		this.guai_attacktype = guai_attacktype;
	}
	public int[] getGuai_level() {
		return guai_level;
	}
	public void setGuai_level(int[] guai_level) {
		this.guai_level = guai_level;
	}
	public int[] getGuai_sp() {
		return guai_sp;
	}
	public void setGuai_sp(int[] guai_sp) {
		this.guai_sp = guai_sp;
	}
	public int[] getGuai_pf() {
		return guai_pf;
	}
	public void setGuai_pf(int[] guai_pf) {
		this.guai_pf = guai_pf;
	}
	public int[] getGuai_hp() {
		return guai_hp;
	}
	public void setGuai_hp(int[] guai_hp) {
		this.guai_hp = guai_hp;
	}
	public int[] getGuai_defend() {
		return guai_defend;
	}
	public void setGuai_defend(int[] guai_defend) {
		this.guai_defend = guai_defend;
	}
	public int[] getGuai_attack() {
		return guai_attack;
	}
	public void setGuai_attack(int[] guai_attack) {
		this.guai_attack = guai_attack;
	}
	public int[] getGuai_physique() {
		return guai_physique;
	}
	public void setGuai_physique(int[] guai_physique) {
		this.guai_physique = guai_physique;
	}
	public int[] getGuai_p_force() {
		return guai_p_force;
	}
	public void setGuai_p_force(int[] guai_p_force) {
		this.guai_p_force = guai_p_force;
	}
	public int[] getGuai_armor() {
		return guai_armor;
	}
	public void setGuai_armor(int[] guai_armor) {
		this.guai_armor = guai_armor;
	}
	public int[] getGuai_agility() {
		return guai_agility;
	}
	public void setGuai_agility(int[] guai_agility) {
		this.guai_agility = guai_agility;
	}
	public int[] getGuai_luck() {
		return guai_luck;
	}
	public void setGuai_luck(int[] guai_luck) {
		this.guai_luck = guai_luck;
	}
	public int[] getGuai_injuryFree() {
		return guai_injuryFree;
	}
	public void setGuai_injuryFree(int[] guai_injuryFree) {
		this.guai_injuryFree = guai_injuryFree;
	}
	public int[] getGuai_wreckDefense() {
		return guai_wreckDefense;
	}
	public void setGuai_wreckDefense(int[] guai_wreckDefense) {
		this.guai_wreckDefense = guai_wreckDefense;
	}
	public int[] getGuai_reduceCrit() {
		return guai_reduceCrit;
	}
	public void setGuai_reduceCrit(int[] guai_reduceCrit) {
		this.guai_reduceCrit = guai_reduceCrit;
	}
	public int[] getGuai_reduceBury() {
		return guai_reduceBury;
	}
	public void setGuai_reduceBury(int[] guai_reduceBury) {
		this.guai_reduceBury = guai_reduceBury;
	}
	public int[] getGuai_attackArea() {
		return guai_attackArea;
	}
	public void setGuai_attackArea(int[] guai_attackArea) {
		this.guai_attackArea = guai_attackArea;
	}
	public int[] getGuai_criticalRate() {
		return guai_criticalRate;
	}
	public void setGuai_criticalRate(int[] guai_criticalRate) {
		this.guai_criticalRate = guai_criticalRate;
	}
	public String[] getGuai_AniFileId() {
		return guai_AniFileId;
	}
	public void setGuai_AniFileId(String[] guai_AniFileId) {
		this.guai_AniFileId = guai_AniFileId;
	}
	public String[] getGuai_ai() {
		return guai_ai;
	}
	public void setGuai_ai(String[] guai_ai) {
		this.guai_ai = guai_ai;
	}
	public String[] getGuai_dialogue() {
		return guai_dialogue;
	}
	public void setGuai_dialogue(String[] guai_dialogue) {
		this.guai_dialogue = guai_dialogue;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public String getPetIcon() {
		return petIcon;
	}
	public void setPetIcon(String petIcon) {
		this.petIcon = petIcon;
	}
	public int getPetType() {
		return petType;
	}
	public void setPetType(int petType) {
		this.petType = petType;
	}
	public int getPetSkillId() {
		return petSkillId;
	}
	public void setPetSkillId(int petSkillId) {
		this.petSkillId = petSkillId;
	}
	public int getPetProbability() {
		return petProbability;
	}
	public void setPetProbability(int petProbability) {
		this.petProbability = petProbability;
	}
	public int getPetParam1() {
		return petParam1;
	}
	public void setPetParam1(int petParam1) {
		this.petParam1 = petParam1;
	}
	public int getPetParam2() {
		return petParam2;
	}
	public void setPetParam2(int petParam2) {
		this.petParam2 = petParam2;
	}
	public String getPetEffect() {
		return petEffect;
	}
	public void setPetEffect(String petEffect) {
		this.petEffect = petEffect;
	}
	public int[] getBuffType() {
		return buffType;
	}
	public void setBuffType(int[] buffType) {
		this.buffType = buffType;
	}
	public int[] getBuffParam() {
		return buffParam;
	}
	public void setBuffParam(int[] buffParam) {
		this.buffParam = buffParam;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getPlayerLevel() {
		return playerLevel;
	}
	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}
	public int getBoyOrGirl() {
		return boyOrGirl;
	}
	public void setBoyOrGirl(int boyOrGirl) {
		this.boyOrGirl = boyOrGirl;
	}
	public int[] getSpecialAttackType() {
		return specialAttackType;
	}
	public void setSpecialAttackType(int[] specialAttackType) {
		this.specialAttackType = specialAttackType;
	}
	public int[] getSpecialAttackParam() {
		return specialAttackParam;
	}
	public void setSpecialAttackParam(int[] specialAttackParam) {
		this.specialAttackParam = specialAttackParam;
	}
	public int getPetVersion() {
		return petVersion;
	}
	public void setPetVersion(int petVersion) {
		this.petVersion = petVersion;
	}
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public int getConstitution() {
		return constitution;
	}
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}
	public int getAgility() {
		return agility;
	}
	public void setAgility(int agility) {
		this.agility = agility;
	}
	public int getLucky() {
		return lucky;
	}
	public void setLucky(int lucky) {
		this.lucky = lucky;
	}
	

}
