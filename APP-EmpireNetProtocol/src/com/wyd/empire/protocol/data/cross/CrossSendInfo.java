package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 类 <code>CheckFriendInfoOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_CheckFriendInfoOk(添加好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CrossSendInfo extends AbstractData {
    private int      battleId;
    private int      playerId;
    private int      friendId;
    private String   playerName;       // 玩家名称
    private int      level;            // 玩家等级
    private String   callName;         // 玩家称号（暂时为空）
    private String   communityName;    // 玩家公会名称
    private int      currentExperience; // 玩家当前经验
    private int      needExperience;   // 玩家该等级升级所需经验
    private int      playerRank;       // 玩家军衔（暂时没有）
    private boolean  vipMark;          // vip标识
    private int      vipLevel;         // vip等级
    private String[] expDoubleMark;    // buff状态标识
    private String   weaponsName;      // 玩家武器名称
    private int      weaponSkillDegree; // 武器熟练度
    private int      critRate;         // 武器暴击率
    private int      playerAttack;     // 武器攻击力
    private int      weaponsArea;      // 武器攻击范围
    private int      hp;               // 生命值
    private int      defend;           // 防御值
    private int      physical;         // 体力值
    private int      honor;            // 荣誉值
    private int      losing;           // 胜率(服务端*100倍，客户端转换成0.00%形式)
    private int      winNum;           // 胜利次数
    private int      playNumber;       // 游戏次数
    private boolean  hasBeenFriend;    // 是否是好友
    private boolean  beOnline;         // 是否在线
    private String   communityPosition; // 公会职务
    private String[] wbUserId;         // 微博uid
    private String[] weiboIcon;        // 微博图片url
    private int      zsLevel;          // 玩家的转生等级
    private int      injuryFree;       // 免伤
    private int      wreckDefense;     // 破防
    private int      reduceCrit;       // 免暴
    private int      reduceBury;       // 免坑
    private int      force;            // 力量
    private int      armor;            // 护甲
    private int      agility;          // 敏捷
    private int      physique;         // 体质
    private int      luck;             // 幸运
    private boolean  doubleCard;       // 双倍经验卡
    private int      fighting;         // 战斗力
    private String   ringIcon;         // 公会戒指图标路径
    private String[] weapSkill;        // 武器技能

    public CrossSendInfo() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossSendInfo);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }

    public int getNeedExperience() {
        return needExperience;
    }

    public void setNeedExperience(int needExperience) {
        this.needExperience = needExperience;
    }

    public int getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }

    public boolean getVipMark() {
        return vipMark;
    }

    public void setVipMark(boolean vipMark) {
        this.vipMark = vipMark;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String[] getExpDoubleMark() {
        return expDoubleMark;
    }

    public void setExpDoubleMark(String[] expDoubleMark) {
        this.expDoubleMark = expDoubleMark;
    }

    public String getWeaponsName() {
        return weaponsName;
    }

    public void setWeaponsName(String weaponsName) {
        this.weaponsName = weaponsName;
    }

    public int getWeaponSkillDegree() {
        return weaponSkillDegree;
    }

    public void setWeaponSkillDegree(int weaponSkillDegree) {
        this.weaponSkillDegree = weaponSkillDegree;
    }

    public int getCritRate() {
        return critRate;
    }

    public void setCritRate(int critRate) {
        this.critRate = critRate;
    }

    public int getPlayerAttack() {
        return playerAttack;
    }

    public void setPlayerAttack(int playerAttack) {
        this.playerAttack = playerAttack;
    }

    public int getWeaponsArea() {
        return weaponsArea;
    }

    public void setWeaponsArea(int weaponsArea) {
        this.weaponsArea = weaponsArea;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getPhysical() {
        return physical;
    }

    public void setPhysical(int physical) {
        this.physical = physical;
    }

    public int getHonor() {
        return honor;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    public int getLosing() {
        return losing;
    }

    public void setLosing(int losing) {
        this.losing = losing;
    }

    public int getWinNum() {
        return winNum;
    }

    public void setWinNum(int winNum) {
        this.winNum = winNum;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public boolean getHasBeenFriend() {
        return hasBeenFriend;
    }

    public void setHasBeenFriend(boolean hasBeenFriend) {
        this.hasBeenFriend = hasBeenFriend;
    }

    public boolean getBeOnline() {
        return beOnline;
    }

    public void setBeOnline(boolean beOnline) {
        this.beOnline = beOnline;
    }

    public String getCommunityPosition() {
        return communityPosition;
    }

    public void setCommunityPosition(String communityPosition) {
        this.communityPosition = communityPosition;
    }

    public String[] getWbUserId() {
        return wbUserId;
    }

    public void setWbUserId(String[] wbUserId) {
        this.wbUserId = wbUserId;
    }

    public String[] getWeiboIcon() {
        return weiboIcon;
    }

    public void setWeiboIcon(String[] weiboIcon) {
        this.weiboIcon = weiboIcon;
    }

    public int getZsLevel() {
        return zsLevel;
    }

    public void setZsLevel(int zsLevel) {
        this.zsLevel = zsLevel;
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

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getPhysique() {
        return physique;
    }

    public void setPhysique(int physique) {
        this.physique = physique;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public boolean getDoubleCard() {
        return doubleCard;
    }

    public void setDoubleCard(boolean doubleCard) {
        this.doubleCard = doubleCard;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public String getRingIcon() {
        return ringIcon;
    }

    public void setRingIcon(String ringIcon) {
        this.ringIcon = ringIcon;
    }

    public String[] getWeapSkill() {
        return weapSkill;
    }

    public void setWeapSkill(String[] weapSkill) {
        this.weapSkill = weapSkill;
    }
}
