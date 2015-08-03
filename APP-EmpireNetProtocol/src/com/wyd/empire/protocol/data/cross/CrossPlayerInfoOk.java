package com.wyd.empire.protocol.data.cross;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CrossPlayerInfoOk extends AbstractData {
	private int      battleId;
    private int      playerId;
    private int      friendId;
    private int id; 				// Id
	private String name; 			// 名称
	private int sex; 				// 性别 0：女 1：男
	private String title; 			// 称号
	private String guildName; 		// 公会名称
	private String position; 		// 公会职务
	private int level; 				// 等级
	private int rank; 				// 玩家军衔
	private int vipLevel; 			// vip等级
	private int winNum; 			// 胜利次数
	private int playNum; 			// 游戏次数
	private int zsLevel; 			// 玩家的转生等级
	private int fighting; 			// 战斗力
	private int force;   			// 力量
	private int hp;     			// 生命
	private int armor; 				// 护甲
	private int attack; 			// 攻击
	private int agility; 			// 敏捷
	private int defend; 			// 防御
	private int physique; 			// 体质
	private int critRate; 			// 暴击
	private int injuryFree; 		// 免伤
	private int reduceCrit; 		// 免暴
	private int physical;           // 体力
	private int wreckDefense;       // 破防
	private int luck;               // 幸运
	private int      age;             // 年龄
	private String   mateName;        // 伴侣
	private String   signature;       // 个性签名
	private int      constellation;   // 星座
	private String   pictureUrl;      // 头像地址.格式："",""
	private boolean  isFriend;        // 是否为好友
	private int[]    itemId;          // 玩家身上装备（包括双倍经验卡）   
	private int[]    maintype;        // 装备主类型
	private int[]    subtype;         // 装备子类型
	private String[] extranInfo;      // 装备扩展信息
	private String   petInfo;         // 宠物信息
	private String   weibo;           // <微博id,微博Icon>  如: "1381179875","http://tp4.sinaimg.cn/1381179875/50/5641224104/1"
    
	public CrossPlayerInfoOk() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPlayerInfoOk);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGuildName() {
		return guildName;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public int getPlayNum() {
		return playNum;
	}

	public void setPlayNum(int playNum) {
		this.playNum = playNum;
	}

	public int getZsLevel() {
		return zsLevel;
	}

	public void setZsLevel(int zsLevel) {
		this.zsLevel = zsLevel;
	}

	public int getFighting() {
		return fighting;
	}

	public void setFighting(int fighting) {
		this.fighting = fighting;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getDefend() {
		return defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	public int getPhysique() {
		return physique;
	}

	public void setPhysique(int physique) {
		this.physique = physique;
	}

	public int getCritRate() {
		return critRate;
	}

	public void setCritRate(int critRate) {
		this.critRate = critRate;
	}

	public int getInjuryFree() {
		return injuryFree;
	}

	public void setInjuryFree(int injuryFree) {
		this.injuryFree = injuryFree;
	}

	public int getReduceCrit() {
		return reduceCrit;
	}

	public void setReduceCrit(int reduceCrit) {
		this.reduceCrit = reduceCrit;
	}

	public int getPhysical() {
		return physical;
	}

	public void setPhysical(int physical) {
		this.physical = physical;
	}

	public int getWreckDefense() {
		return wreckDefense;
	}

	public void setWreckDefense(int wreckDefense) {
		this.wreckDefense = wreckDefense;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getConstellation() {
		return constellation;
	}

	public void setConstellation(int constellation) {
		this.constellation = constellation;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public boolean getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getMaintype() {
		return maintype;
	}

	public void setMaintype(int[] maintype) {
		this.maintype = maintype;
	}

	public int[] getSubtype() {
		return subtype;
	}

	public void setSubtype(int[] subtype) {
		this.subtype = subtype;
	}

	public String[] getExtranInfo() {
		return extranInfo;
	}

	public void setExtranInfo(String[] extranInfo) {
		this.extranInfo = extranInfo;
	}

	public String getPetInfo() {
		return petInfo;
	}

	public void setPetInfo(String petInfo) {
		this.petInfo = petInfo;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	

	
}
