package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 增加宠物
 * @author zengxc
 *
 */
public class AddPet extends AbstractData {

	private int[]     petId;                 // 宠物序号
	private String[]  name;                  // 物品名字
    private String[]  icon;                  // 宠物图标
    private String[]  picture;               // 宠物大图
    private int[]     level;                 // 宠物级别
    private int[]     quality;               // 类型0普通1高级
    private int[]     hp;                    // 生命
    private int[]     attack;                // 攻击
    private int[]     defend;                // 防御
    private int[]     hasHp;                 // 已有的生命
    private int[]     hasAttack;             // 已有的攻击
    private int[]     hasDefend;             // 已有的防御
    private String[]  skillIcon;             // 技能图标
    private int[]     startExp;              // 当前级别开始的经验
    private int[]     currExp;               // 当前经验
    private int[]     needExp;               // 升级所需要的经验
    private int[]     inTrain;               // 是否在训练中0否1是
    private int[]     trainNeedTime;         // 训练所需要总时间
    private int[]     trainAddExp;           // 训练完成增加的经验 
    private int[]     trainPrice;            // 训练所需要费用（金币）
    private int[]     cultureGold;           // 培养所需要金币
    private int[]     cultureDiamond;        // 培养所需要钻石
    private int[]     culHp;                 // 培养生命
    private int[]     culAttack;             // 培养攻击
    private int[]     culDefend;             // 培养防御
    private int[]     culMaxHp;              // 培养最大生命
    private int[]     culMaxAttack;          // 培养最大攻击
    private int[]     culMaxDefend;          // 培养最大防御
    private int[]     isPlayed;              // 是否已出战
    private	String[]  skillDesc;		     // 宠物技能描述
    private	String[]  skillName;             // 宠物技能名称
    private int[]     trainAddLevel;         // 训练完成增加的经验 
    private int[]     skillId;               // 宠物技能ID
	
	
	public AddPet(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_AddPet, sessionId, serial);
	}

	public AddPet() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_AddPet);
	}

	public int[] getPetId() {
		return petId;
	}

	public void setPetId(int[] petId) {
		this.petId = petId;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getIcon() {
		return icon;
	}

	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	public String[] getPicture() {
		return picture;
	}

	public void setPicture(String[] picture) {
		this.picture = picture;
	}

	public int[] getLevel() {
		return level;
	}

	public void setLevel(int[] level) {
		this.level = level;
	}

	public int[] getQuality() {
		return quality;
	}

	public void setQuality(int[] quality) {
		this.quality = quality;
	}

	public int[] getHp() {
		return hp;
	}

	public void setHp(int[] hp) {
		this.hp = hp;
	}

	public int[] getAttack() {
		return attack;
	}

	public void setAttack(int[] attack) {
		this.attack = attack;
	}

	public int[] getDefend() {
		return defend;
	}

	public void setDefend(int[] defend) {
		this.defend = defend;
	}

	public int[] getHasHp() {
		return hasHp;
	}

	public void setHasHp(int[] hasHp) {
		this.hasHp = hasHp;
	}

	public int[] getHasAttack() {
		return hasAttack;
	}

	public void setHasAttack(int[] hasAttack) {
		this.hasAttack = hasAttack;
	}

	public int[] getHasDefend() {
		return hasDefend;
	}

	public void setHasDefend(int[] hasDefend) {
		this.hasDefend = hasDefend;
	}

	public String[] getSkillIcon() {
		return skillIcon;
	}

	public void setSkillIcon(String[] skillIcon) {
		this.skillIcon = skillIcon;
	}

	public int[] getStartExp() {
		return startExp;
	}

	public void setStartExp(int[] startExp) {
		this.startExp = startExp;
	}

	public int[] getCurrExp() {
		return currExp;
	}

	public void setCurrExp(int[] currExp) {
		this.currExp = currExp;
	}

	public int[] getNeedExp() {
		return needExp;
	}

	public void setNeedExp(int[] needExp) {
		this.needExp = needExp;
	}

	public int[] getInTrain() {
		return inTrain;
	}

	public void setInTrain(int[] inTrain) {
		this.inTrain = inTrain;
	}

	public int[] getTrainNeedTime() {
		return trainNeedTime;
	}

	public void setTrainNeedTime(int[] trainNeedTime) {
		this.trainNeedTime = trainNeedTime;
	}

	public int[] getTrainAddExp() {
		return trainAddExp;
	}

	public void setTrainAddExp(int[] trainAddExp) {
		this.trainAddExp = trainAddExp;
	}

	public int[] getTrainPrice() {
		return trainPrice;
	}

	public void setTrainPrice(int[] trainPrice) {
		this.trainPrice = trainPrice;
	}

	public int[] getCultureGold() {
		return cultureGold;
	}

	public void setCultureGold(int[] cultureGold) {
		this.cultureGold = cultureGold;
	}

	public int[] getCultureDiamond() {
		return cultureDiamond;
	}

	public void setCultureDiamond(int[] cultureDiamond) {
		this.cultureDiamond = cultureDiamond;
	}

	public int[] getCulHp() {
		return culHp;
	}

	public void setCulHp(int[] culHp) {
		this.culHp = culHp;
	}

	public int[] getCulAttack() {
		return culAttack;
	}

	public void setCulAttack(int[] culAttack) {
		this.culAttack = culAttack;
	}

	public int[] getCulDefend() {
		return culDefend;
	}

	public void setCulDefend(int[] culDefend) {
		this.culDefend = culDefend;
	}

	public int[] getCulMaxHp() {
		return culMaxHp;
	}

	public void setCulMaxHp(int[] culMaxHp) {
		this.culMaxHp = culMaxHp;
	}

	public int[] getCulMaxAttack() {
		return culMaxAttack;
	}

	public void setCulMaxAttack(int[] culMaxAttack) {
		this.culMaxAttack = culMaxAttack;
	}

	public int[] getCulMaxDefend() {
		return culMaxDefend;
	}

	public void setCulMaxDefend(int[] culMaxDefend) {
		this.culMaxDefend = culMaxDefend;
	}

	public int[] getIsPlayed() {
		return isPlayed;
	}

	public void setIsPlayed(int[] isPlayed) {
		this.isPlayed = isPlayed;
	}

	public String[] getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String[] skillDesc) {
		this.skillDesc = skillDesc;
	}

	public String[] getSkillName() {
		return skillName;
	}

	public void setSkillName(String[] skillName) {
		this.skillName = skillName;
	}

	public int[] getTrainAddLevel() {
		return trainAddLevel;
	}

	public void setTrainAddLevel(int[] trainAddLevel) {
		this.trainAddLevel = trainAddLevel;
	}

	public int[] getSkillId() {
		return skillId;
	}

	public void setSkillId(int[] skillId) {
		this.skillId = skillId;
	}

	
	
	
	
}
