package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

/**
 * 玩家信息
 * 
 * @author zengxc
 * 
 */
public class PlayerInfo extends AbstractData {
	private int id; 				// Id
	private String name; 			// 名称
	private int sex; 				// 性别 0：女 1：男
	private String title; 			// 称号
	private String guildName; 		// 公会名称
	private String position; 		// 公会职务
	private int level; 				// 等级
	private int exp; 				// 当前经验
	private int maxExp; 			// 该等级升级所需经验
	private int rank; 				// 玩家军衔
	private int vipLevel; 			// vip等级
	private int redDiamond; 		// 红钻
	private int blueDiamond; 		// 蓝钻
	private int gold; 				// 金币
	private int medal; 				// 勋章
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
	private int age;                // 年龄
	private String mateName;        // 伴侣
	private String signature;       // 个性签名
	private int constellation;      // 星座
	private String pictureUrl;      // 头像地址.格式："",""
	private String pendingUrl;      // 待审头像地址.格式："","" 
	private int    cardSeatNum;     // 卡牌位置数量
	private String buff;            // 生效的BUFF。格式：["exp","life"]
	private int    vigor;           // 当前活力值
	private int    maxVigor;        // 最活力值 
	private int    buyVigorTimes;   // 当前购买活力次数
	private int    maxBuyVigorTimes;// 最大购买活力次数
	private int    starSoulLeve;    // 星魂等级
	private int    soulDot;         // 当前星点
	private	int				  useLimitNumber;		//使用勋章上限数
    private int				  useTodayNumber;		//今日还可以使用勋章数
//	private boolean			  practiceStatus;		//是否激活
	private String            practiceAttributeExp; // 属性经验值,分割
	private int               practiceLeve;         // 修炼当前等级
	private	int				  seniorMedlNumber;	//玩家拥有的高级勋章总数
	private String weibo;          // <微博id,微博Icon>  如: "1381179875","http://tp4.sinaimg.cn/1381179875/50/5641224104/1"
	private int    petBarNum;      // 宠物栏个数
	


	public PlayerInfo(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_PlayerInfo, sessionId, serial);
	}

	public PlayerInfo() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_PlayerInfo);
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
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

	public int getRedDiamond() {
		return redDiamond;
	}

	public void setRedDiamond(int redDiamond) {
		this.redDiamond = redDiamond;
	}

	public int getBlueDiamond() {
		return blueDiamond;
	}

	public void setBlueDiamond(int blueDiamond) {
		this.blueDiamond = blueDiamond;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
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

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
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

	public String getPendingUrl() {
		return pendingUrl;
	}

	public void setPendingUrl(String pendingUrl) {
		this.pendingUrl = pendingUrl;
	}

	public int getCardSeatNum() {
		return cardSeatNum;
	}

	public void setCardSeatNum(int cardSeatNum) {
		this.cardSeatNum = cardSeatNum;
	}

	public String getBuff() {
		return buff;
	}

	public void setBuff(String buff) {
		this.buff = buff;
	}

	public int getVigor() {
		return vigor;
	}

	public void setVigor(int vigor) {
		this.vigor = vigor;
	}

	public int getMaxVigor() {
		return maxVigor;
	}

	public void setMaxVigor(int maxVigor) {
		this.maxVigor = maxVigor;
	}

	public int getBuyVigorTimes() {
		return buyVigorTimes;
	}

	public void setBuyVigorTimes(int buyVigorTimes) {
		this.buyVigorTimes = buyVigorTimes;
	}

	public int getMaxBuyVigorTimes() {
		return maxBuyVigorTimes;
	}

	public void setMaxBuyVigorTimes(int maxBuyVigorTimes) {
		this.maxBuyVigorTimes = maxBuyVigorTimes;
	}

	public int getStarSoulLeve() {
		return starSoulLeve;
	}

	public void setStarSoulLeve(int starSoulLeve) {
		this.starSoulLeve = starSoulLeve;
	}

	public int getSoulDot() {
		return soulDot;
	}

	public void setSoulDot(int soulDot) {
		this.soulDot = soulDot;
	}

	public int getUseLimitNumber() {
		return useLimitNumber;
	}

	public void setUseLimitNumber(int useLimitNumber) {
		this.useLimitNumber = useLimitNumber;
	}

	public int getUseTodayNumber() {
		return useTodayNumber;
	}

	public void setUseTodayNumber(int useTodayNumber) {
		this.useTodayNumber = useTodayNumber;
	}

//	public boolean getPracticeStatus() {
//		return practiceStatus;
//	}
//
//	public void setPracticeStatus(boolean practiceStatus) {
//		this.practiceStatus = practiceStatus;
//	}

	public String getPracticeAttributeExp() {
		return practiceAttributeExp;
	}

	public void setPracticeAttributeExp(String practiceAttributeExp) {
		this.practiceAttributeExp = practiceAttributeExp;
	}

	public int getPracticeLeve() {
		return practiceLeve;
	}

	public void setPracticeLeve(int practiceLeve) {
		this.practiceLeve = practiceLeve;
	}

	public int getSeniorMedlNumber() {
		return seniorMedlNumber;
	}

	public void setSeniorMedlNumber(int seniorMedlNumber) {
		this.seniorMedlNumber = seniorMedlNumber;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public int getPetBarNum() {
		return petBarNum;
	}

	public void setPetBarNum(int petBarNum) {
		this.petBarNum = petBarNum;
	}

	

}
