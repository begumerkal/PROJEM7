package com.app.empire.protocol.data.room;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class MakePairOk extends AbstractData {
    private int      battleId;
    private int      battleMode;
    private String   battleMap;
    private int      playerCount;
    private int[]    playerId;
    private int[]    roomId;
    private String[] playerName;
    private int[]    playerLevel;
    private int[]    boyOrGirl;
    private String[] suit_head;
    private String[] suit_face;
    private String[] suit_body;
    private String[] suit_weapon;
    private int[]    weapon_type;
    private int[]    camp;
    private int[]    maxHP;
    private int[]    maxPF;
    private int[]    maxSP;
    private int[]    attack;
    private int[]    bigSkillAttack;
    private int[]    crit;
    private int[]    defence;
    private int[]    bigSkillType;
    private int[]    explodeRadius;
    private int[]    item_id;
    private int[]    item_used;
    private String[] item_img;
    private String[] item_name;
    private String[] item_desc;
    private int[]    item_type;
    private int[]    item_subType;
    private int[]    item_param1;
    private int[]    item_param2;
    private int[]    item_ConsumePower;
    private int[]    specialAttackType;
    private int[]    specialAttackParam;
    private int[]    playerBuffCount;    // 表示每一个player,buff的数量,如果没有要填零
    private int[]    buffType;           // 按角色顺序填入
    private int[]    buffParam1;         // 不同类型参数意义不同(按角色顺序填入)
    private int[]    buffParam2;         // 预留参数(按角色顺序填入)
    private int[]    buffParam3;         // 预留参数(按角色顺序填入)
    private String[] suit_wing;
    private String[] player_title;
    private String[] player_community;
    private int[]    weaponSkillPlayerId; // 武器技能属于那一个角色的
    private String[] weaponSkillName;    // 武器技能名称
    private int[]    weaponSkillType;    // 武器技能类型
    private int[]    weaponSkillChance;  // 武器技能出现概率(0~10000)
    private int[]    weaponSkillParam1;  // 武器技能参数1
    private int[]    weaponSkillParam2;  // 武器技能参数2
    private boolean  beEnemyCommunity;   // 是否是敌对公会
    private int[]    injuryFree;         // 免伤
    private int[]    wreckDefense;       // 破防
    private int[]    reduceCrit;         // 免暴
    private int[]    reduceBury;         // 免坑
    private int[]    zsleve;             // 玩家转生等级
    private int[]    skillful;           // 玩家武器熟练度
    private int[]    petId;              // 宠物id，0表示无宠物
    private String[] petIcon;            // 宠物icon
    private int[]    petType;            // 宠物类型
    private int[]    petSkillId;         // 宠物技能id
    private int[]    petProbability;     // 宠物攻击概率
    private int[]    petParam1;          // 宠物参数1
    private int[]    petParam2;          // 宠物参数2
    private String[] petEffect;          // 宠物攻击特效名称
    private String[] serverName;         // 玩家所在分区名称
    private int      selfId;             // 玩家的id
    private int[]    petVersion;         // 宠物版本 1 旧版，2新版
    private int[]    power;              // 力量
    private int[]    armor;              // 护甲
    private int[]    fighting;           // 玩家的战斗力
    private int[]    winRate;            // 玩家的胜率(万分比)
    private String   robotSkill;         // 机器人使用的技能信息
    private int[]    constitution;       // 体质
    private int[]    agility;            // 敏捷
    private int[]    lucky;              // 幸运

    public MakePairOk(int sessionId, int serial) {
        super(Protocol.MAIN_ROOM, Protocol.ROOM_MakePairOk, sessionId, serial);
    }

    public MakePairOk() {
        super(Protocol.MAIN_ROOM, Protocol.ROOM_MakePairOk);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getBattleMode() {
        return battleMode;
    }

    public void setBattleMode(int battleMode) {
        this.battleMode = battleMode;
    }

    public String getBattleMap() {
        return battleMap;
    }

    public void setBattleMap(String battleMap) {
        this.battleMap = battleMap;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int[] getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int[] playerId) {
        this.playerId = playerId;
    }

    public int[] getRoomId() {
        return roomId;
    }

    public void setRoomId(int[] roomId) {
        this.roomId = roomId;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int[] playerLevel) {
        this.playerLevel = playerLevel;
    }

    public int[] getBoyOrGirl() {
        return boyOrGirl;
    }

    public void setBoyOrGirl(int[] boyOrGirl) {
        this.boyOrGirl = boyOrGirl;
    }

    public String[] getSuit_head() {
        return suit_head;
    }

    public void setSuit_head(String[] suit_head) {
        this.suit_head = suit_head;
    }

    public String[] getSuit_face() {
        return suit_face;
    }

    public void setSuit_face(String[] suit_face) {
        this.suit_face = suit_face;
    }

    public String[] getSuit_body() {
        return suit_body;
    }

    public void setSuit_body(String[] suit_body) {
        this.suit_body = suit_body;
    }

    public String[] getSuit_weapon() {
        return suit_weapon;
    }

    public void setSuit_weapon(String[] suit_weapon) {
        this.suit_weapon = suit_weapon;
    }

    public int[] getWeapon_type() {
        return weapon_type;
    }

    public void setWeapon_type(int[] weapon_type) {
        this.weapon_type = weapon_type;
    }

    public int[] getCamp() {
        return camp;
    }

    public void setCamp(int[] camp) {
        this.camp = camp;
    }

    public int[] getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int[] maxHP) {
        this.maxHP = maxHP;
    }

    public int[] getMaxPF() {
        return maxPF;
    }

    public void setMaxPF(int[] maxPF) {
        this.maxPF = maxPF;
    }

    public int[] getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(int[] maxSP) {
        this.maxSP = maxSP;
    }

    public int[] getAttack() {
        return attack;
    }

    public void setAttack(int[] attack) {
        this.attack = attack;
    }

    public int[] getBigSkillAttack() {
        return bigSkillAttack;
    }

    public void setBigSkillAttack(int[] bigSkillAttack) {
        this.bigSkillAttack = bigSkillAttack;
    }

    public int[] getCrit() {
        return crit;
    }

    public void setCrit(int[] crit) {
        this.crit = crit;
    }

    public int[] getDefence() {
        return defence;
    }

    public void setDefence(int[] defence) {
        this.defence = defence;
    }

    public int[] getBigSkillType() {
        return bigSkillType;
    }

    public void setBigSkillType(int[] bigSkillType) {
        this.bigSkillType = bigSkillType;
    }

    public int[] getExplodeRadius() {
        return explodeRadius;
    }

    public void setExplodeRadius(int[] explodeRadius) {
        this.explodeRadius = explodeRadius;
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

    public int[] getPlayerBuffCount() {
        return playerBuffCount;
    }

    public void setPlayerBuffCount(int[] playerBuffCount) {
        this.playerBuffCount = playerBuffCount;
    }

    public int[] getBuffType() {
        return buffType;
    }

    public void setBuffType(int[] buffType) {
        this.buffType = buffType;
    }

    public int[] getBuffParam1() {
        return buffParam1;
    }

    public void setBuffParam1(int[] buffParam1) {
        this.buffParam1 = buffParam1;
    }

    public int[] getBuffParam2() {
        return buffParam2;
    }

    public void setBuffParam2(int[] buffParam2) {
        this.buffParam2 = buffParam2;
    }

    public int[] getBuffParam3() {
        return buffParam3;
    }

    public void setBuffParam3(int[] buffParam3) {
        this.buffParam3 = buffParam3;
    }

    public String[] getSuit_wing() {
        return suit_wing;
    }

    public void setSuit_wing(String[] suit_wing) {
        this.suit_wing = suit_wing;
    }

    public String[] getPlayer_title() {
        return player_title;
    }

    public void setPlayer_title(String[] player_title) {
        this.player_title = player_title;
    }

    public String[] getPlayer_community() {
        return player_community;
    }

    public void setPlayer_community(String[] player_community) {
        this.player_community = player_community;
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

    public boolean isBeEnemyCommunity() {
        return beEnemyCommunity;
    }

    public void setBeEnemyCommunity(boolean beEnemyCommunity) {
        this.beEnemyCommunity = beEnemyCommunity;
    }

    public int[] getInjuryFree() {
        return injuryFree;
    }

    public void setInjuryFree(int[] injuryFree) {
        this.injuryFree = injuryFree;
    }

    public int[] getWreckDefense() {
        return wreckDefense;
    }

    public void setWreckDefense(int[] wreckDefense) {
        this.wreckDefense = wreckDefense;
    }

    public int[] getReduceCrit() {
        return reduceCrit;
    }

    public void setReduceCrit(int[] reduceCrit) {
        this.reduceCrit = reduceCrit;
    }

    public int[] getReduceBury() {
        return reduceBury;
    }

    public void setReduceBury(int[] reduceBury) {
        this.reduceBury = reduceBury;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }

    public int[] getSkillful() {
        return skillful;
    }

    public void setSkillful(int[] skillful) {
        this.skillful = skillful;
    }

    public int[] getPetId() {
        return petId;
    }

    public void setPetId(int[] petId) {
        this.petId = petId;
    }

    public String[] getPetIcon() {
        return petIcon;
    }

    public void setPetIcon(String[] petIcon) {
        this.petIcon = petIcon;
    }

    public int[] getPetType() {
        return petType;
    }

    public void setPetType(int[] petType) {
        this.petType = petType;
    }

    public int[] getPetSkillId() {
        return petSkillId;
    }

    public void setPetSkillId(int[] petSkillId) {
        this.petSkillId = petSkillId;
    }

    public int[] getPetProbability() {
        return petProbability;
    }

    public void setPetProbability(int[] petProbability) {
        this.petProbability = petProbability;
    }

    public int[] getPetParam1() {
        return petParam1;
    }

    public void setPetParam1(int[] petParam1) {
        this.petParam1 = petParam1;
    }

    public int[] getPetParam2() {
        return petParam2;
    }

    public void setPetParam2(int[] petParam2) {
        this.petParam2 = petParam2;
    }

    public String[] getPetEffect() {
        return petEffect;
    }

    public void setPetEffect(String[] petEffect) {
        this.petEffect = petEffect;
    }

    public String[] getServerName() {
        return serverName;
    }

    public void setServerName(String[] serverName) {
        this.serverName = serverName;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public int[] getPetVersion() {
        return petVersion;
    }

    public void setPetVersion(int[] petVersion) {
        this.petVersion = petVersion;
    }

    public int[] getPower() {
        return power;
    }

    public void setPower(int[] power) {
        this.power = power;
    }

    public int[] getArmor() {
        return armor;
    }

    public void setArmor(int[] armor) {
        this.armor = armor;
    }

    public int[] getFighting() {
        return fighting;
    }

    public void setFighting(int[] fighting) {
        this.fighting = fighting;
    }

    public int[] getWinRate() {
        return winRate;
    }

    public void setWinRate(int[] winRate) {
        this.winRate = winRate;
    }

    public String getRobotSkill() {
        return robotSkill;
    }

    public void setRobotSkill(String robotSkill) {
        this.robotSkill = robotSkill;
    }

    public int[] getConstitution() {
        return constitution;
    }

    public void setConstitution(int[] constitution) {
        this.constitution = constitution;
    }

    public int[] getAgility() {
        return agility;
    }

    public void setAgility(int[] agility) {
        this.agility = agility;
    }

    public int[] getLucky() {
        return lucky;
    }

    public void setLucky(int[] lucky) {
        this.lucky = lucky;
    }
}
