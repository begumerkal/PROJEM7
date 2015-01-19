package com.wyd.empire.protocol.data.worldbosshall;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 开始战斗成功
 * 对战模式  5
 * @author zengxc
 *
 */
public class StartOk extends AbstractData {
	private int      battleId;             // 战斗组Id
    private int      battleMode = 5;       // 对战模式  
    private String   bossMapName;          // 副本名称
    private String   battleMap;            // 战斗地图资源名称
    private String   battlemap_icon;       // 完成条件图标
	private int      playerCount;          // 玩家数量
    private int[]    playerId;             // 所有玩家id
    private int[]    roomId;               // 所有玩家所在房间id
    private int[]    posX;                 // 坐标X
    private int[]    posY;                 // 坐标Y
    private String[] playerName;           // 房间内玩家昵称
    private int[]    playerLevel;          // 房间内玩家等级
    private int[]    boyOrGirl;            // 男还是女
    private String[] suit_head;            // 着装串头
    private String[] suit_face;            // 着装串脸
    private String[] suit_body;            // 着装串身
    private String[] suit_weapon;          // 着装串武器
    private int[]    weapon_type;          // 武器类型 0:投掷类 1:射击类
    private int[]    camp;                 // 房间内玩家所属阵营
    private int[]    maxHP;                // 最大血量
    private int[]    maxPF;                // 最大体力值
    private int[]    maxSP;                // 最大怒气
    private int[]    attack;               // 普攻击力
    private int[]    bigSkillAttack;       // 大招攻击力
    private int[]    critRate;             // 爆击攻击力比率
    private int[]    defence;              // 防御力
    private int[]    bigSkillType;         // 大招类型
    private int[]    explodeRadius;        // 爆破范围
    private int[]    item_id;              // 技能道具ID
    private int[]    item_used;            // 是否装备道具（1有装备，0没装备，-1锁）
    private String[] item_img;             // 道具图像路径
    private String[] item_name;            // 道具名称
    private String[] item_desc;            // 道具描述
    private int[]    item_type;            // 道具类型
    private int[]    item_subType;         // 道具子类型
    private int[]    item_param1;          // 参数1
    private int[]    item_param2;          // 参数2
    private int[]    item_ConsumePower;    // 消耗体力
    private int[]    specialAttackType;    // 附加的特殊攻击类型
    private int[]    specialAttackParam;   // 附加的特殊攻击数值参数
    private int      guaiCount;            // 怪数量
    private int[]    guaiBattleId;         // 在本次对战中的独立id,每一个怪都不一样,如果是被招出来的怪是-1,即一开始不在对战出现
    private int[]    guaiId;               // 怪在怪表中的id
    private int[]    guaiposX;             // 怪坐标X
    private int[]    guaiposY;             // 怪坐标Y
    private String[] guai_name;            // 名字
    private int[]    guai_camp;            // 怪的阵营，0是玩家队友，1是怪
    private int[]    guai_sex;             // 0:男 1:女
    private String[] guai_suit_head;       // 着装头(如果type==1时,值为stand)
    private String[] guai_suit_face;       // 着装脸(如果type==1时,值为stand)
    private String[] guai_suit_body;       // 着装身(如果type==1时,值为stand)
    private String[] guai_suit_weapon;     // 着装武器(如果type==1时,值为stand)
    private int[]    guai_weapon_type;     // 武器类型
    private int[]    guai_type;            // 0:有着装的小怪 1:没有着装的小怪 2:boss
    private int[]    guai_level;           // 等级
    private int[]    guai_attacktype;      // 攻击类型
    private int[]    guai_hp;              // 生命值
    private int[]    guai_sp;              // 怒气值
    private int[]    guai_pf;              // 体力
    private int[]    guai_defend;          // 防御力
    private int[]    guai_attack;          // 攻击力
    private int[]    guai_attackArea;      // 攻击范围
    private int[]    guai_criticalRate;    // 万份比数值(放大一万陪) 增加暴击率
    private int[]    guai_bigSkillType;    // 大招类型.没有大招:-1
    private String[] guai_explode;         // 爆炸图(type==0时,这里不需要,直接根武器相关)
    private String[] guai_broken;          // 坑图(type==0时,这里不需要,直接根武器相关)
    private String[] guai_AniFileId;       // 动画文件id.格式:[boss[id]]或[guai[id]],如:boss1,guai1
    private int[]    guai_could_build_guai; // 是否可以招唤小怪
    private int[]    guai_build_guai_id;   // 放出的小怪在怪表中的id
    private int      idcount;              // id数量最少10个
    private int[]    build_guai_id_list;   // 如果招唤小怪就会给其这些后续的id,是不会与之前的哪此冲突的
    private int[]    playerBuffCount;      // 表示每一个player,buff的数量,如果没有要填零
    private int[]    buffType;             // 按角色顺序填入
    private int[]    buffParam1;           // 不同类型参数意义不同(按角色顺序填入)
    private int[]    buffParam2;           // 预留参数(按角色顺序填入)
    private int[]    buffParam3;           // 预留参数(按角色顺序填入)
    private String[] suit_wing;
    private String[] player_title;
    private String[] player_community;
    private int[]    weaponSkillPlayerId;  // 武器技能属于那一个角色的
    private String[] weaponSkillName;      // 武器技能名称
    private int[]    weaponSkillType;      // 武器技能类型
    private int[]    weaponSkillChance;    // 武器技能出现概率(0~10000)
    private int[]    weaponSkillParam1;    // 武器技能参数1
    private int[]    weaponSkillParam2;    // 武器技能参数2
    private int[]    injuryFree;           // 免伤
    private int[]    wreckDefense;         // 破防
    private int[]    reduceCrit;           // 免暴
    private int[]    reduceBury;           // 免坑
    private int[]    zsleve;               // 玩家转生等级
    private int[]    skillful;             // 玩家武器熟练度
    private int      difficulty;           // 副本难度(0=普通,1=困难,2=地狱)
    private int[]    guai_injuryFree;      // 免伤
    private int[]    guai_wreckDefense;    // 破防
    private int[]    guai_reduceCrit;      // 免暴
    private int[]    guai_reduceBury;      // 免坑
    private int[]    skillHurt;            // boss的技能伤害
    private int[]    petId;                // 宠物id，0表示无宠物
    private String[] petIcon;              // 宠物icon
    private int[]    petType;              // 宠物类型
    private int[]    petSkillId;           // 宠物技能id
    private int[]    petProbability;       // 宠物攻击概率
    private int[]    petParam1;            // 宠物参数1
    private int[]    petParam2;            // 宠物参数2
    private String[] petEffect;            // 宠物攻击特效名称
    private int[]    petVersion;           // 宠物版本 1 旧版，2新版
    private int[]	 force;				   // 力量
    private int[] 	 armor;				   // 护甲
    private int[]	 constitution;	       // 体质
    private int[]	 agility;	           // 敏捷
    private int[]	 lucky;     	       // 幸运

    public StartOk(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_StartOk, sessionId, serial);
    }
	public StartOk(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_StartOk);
	}

    public int getBattleId() {
        return battleId;
    }

   

    public int getBattleMode() {
        return battleMode;
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

    public int[] getPosX() {
        return posX;
    }

    public void setPosX(int[] posX) {
        this.posX = posX;
    }

    public int[] getPosY() {
        return posY;
    }

    public void setPosY(int[] posY) {
        this.posY = posY;
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

    public int[] getCritRate() {
        return critRate;
    }

    public void setCritRate(int[] critRate) {
        this.critRate = critRate;
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

    public int getGuaiCount() {
        return guaiCount;
    }

    public void setGuaiCount(int guaiCount) {
        this.guaiCount = guaiCount;
    }

    public int[] getGuaiBattleId() {
        return guaiBattleId;
    }

    public void setGuaiBattleId(int[] guaiBattleId) {
        this.guaiBattleId = guaiBattleId;
    }

    public int[] getGuaiId() {
        return guaiId;
    }

    public void setGuaiId(int[] guaiId) {
        this.guaiId = guaiId;
    }

    public int[] getGuaiposX() {
        return guaiposX;
    }

    public void setGuaiposX(int[] guaiposX) {
        this.guaiposX = guaiposX;
    }

    public int[] getGuaiposY() {
        return guaiposY;
    }

    public void setGuaiposY(int[] guaiposY) {
        this.guaiposY = guaiposY;
    }

    public String[] getGuai_name() {
        return guai_name;
    }

    public void setGuai_name(String[] guai_name) {
        this.guai_name = guai_name;
    }

    public int[] getGuai_camp() {
        return guai_camp;
    }

    public void setGuai_camp(int[] guai_camp) {
        this.guai_camp = guai_camp;
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

    public int[] getGuai_level() {
        return guai_level;
    }

    public void setGuai_level(int[] guai_level) {
        this.guai_level = guai_level;
    }

    public int[] getGuai_attacktype() {
        return guai_attacktype;
    }

    public void setGuai_attacktype(int[] guai_attacktype) {
        this.guai_attacktype = guai_attacktype;
    }

    public int[] getGuai_hp() {
        return guai_hp;
    }

    public void setGuai_hp(int[] guai_hp) {
        this.guai_hp = guai_hp;
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

    public int[] getGuai_bigSkillType() {
        return guai_bigSkillType;
    }

    public void setGuai_bigSkillType(int[] guai_bigSkillType) {
        this.guai_bigSkillType = guai_bigSkillType;
    }

    public String[] getGuai_explode() {
        return guai_explode;
    }

    public void setGuai_explode(String[] guai_explode) {
        this.guai_explode = guai_explode;
    }

    public String[] getGuai_broken() {
        return guai_broken;
    }

    public void setGuai_broken(String[] guai_broken) {
        this.guai_broken = guai_broken;
    }

    public String[] getGuai_AniFileId() {
        return guai_AniFileId;
    }

    public void setGuai_AniFileId(String[] guai_AniFileId) {
        this.guai_AniFileId = guai_AniFileId;
    }

    public int[] getGuai_could_build_guai() {
        return guai_could_build_guai;
    }

    public void setGuai_could_build_guai(int[] guai_could_build_guai) {
        this.guai_could_build_guai = guai_could_build_guai;
    }

    public int[] getGuai_build_guai_id() {
        return guai_build_guai_id;
    }

    public void setGuai_build_guai_id(int[] guai_build_guai_id) {
        this.guai_build_guai_id = guai_build_guai_id;
    }

    public int getIdcount() {
        return idcount;
    }

    public void setIdcount(int idcount) {
        this.idcount = idcount;
    }

    public int[] getBuild_guai_id_list() {
        return build_guai_id_list;
    }

    public void setBuild_guai_id_list(int[] build_guai_id_list) {
        this.build_guai_id_list = build_guai_id_list;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public int[] getSkillHurt() {
        return skillHurt;
    }

    public void setSkillHurt(int[] skillHurt) {
        this.skillHurt = skillHurt;
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
    public String getBattlemap_icon() {
		return battlemap_icon;
	}
	public void setBattlemap_icon(String battlemap_icon) {
		this.battlemap_icon = battlemap_icon;
	}
	public int[] getPetVersion() {
		return petVersion;
	}
	public void setPetVersion(int[] petVersion) {
		this.petVersion = petVersion;
	}
	public int[] getForce() {
		return force;
	}
	public void setForce(int[] force) {
		this.force = force;
	}
	public int[] getArmor() {
		return armor;
	}
	public void setArmor(int[] armor) {
		this.armor = armor;
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
