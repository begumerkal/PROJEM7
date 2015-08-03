package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetAllPetListOk extends AbstractData {
	public GetAllPetListOk(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_GetAllPetListOk, sessionId, serial);
	}

	public GetAllPetListOk() {
		super(Protocol.MAIN_PET, Protocol.PET_GetAllPetListOk);
	}
	
	private	int[]	petId;		//宠物ID
	private	String[]	name;		//名称
	private	String[]	icon;		//宠物图标
	private	String[]	picture;		//宠物大图
	private	int[]	petLevel;		//级别
	private	int[]	quality;		//类型0普通1高级
	private	int[]	hp;		//生命 
	private	int[]	attack;		//攻击
	private	int[]	defend;		//防御
	private	String[]	skillIcon;		//技能图标
	private	int[]	getLevel;		//宠物召唤等级
	private	int[]	getMoney;		//购买宠物金币
	private	int	petNum;		//玩家携带宠物上限
	private	int	playerPetNum;		//玩家拥有宠物数
	private	int[]	playerGet;		//玩家是否拥有宠物（0不拥有1拥有）
	private int[]   payType;		//宠物的支付类型
	private	String[] skillDesc;		//宠物技能描述
	
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

	public int[] getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int[] petLevel) {
		this.petLevel = petLevel;
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

	public String[] getSkillIcon() {
		return skillIcon;
	}

	public void setSkillIcon(String[] skillIcon) {
		this.skillIcon = skillIcon;
	}

	public int[] getGetLevel() {
		return getLevel;
	}

	public void setGetLevel(int[] getLevel) {
		this.getLevel = getLevel;
	}

	public int[] getGetMoney() {
		return getMoney;
	}

	public void setGetMoney(int[] getMoney) {
		this.getMoney = getMoney;
	}

	public int getPetNum() {
		return petNum;
	}

	public void setPetNum(int petNum) {
		this.petNum = petNum;
	}

	public int getPlayerPetNum() {
		return playerPetNum;
	}

	public void setPlayerPetNum(int playerPetNum) {
		this.playerPetNum = playerPetNum;
	}

	public int[] getPlayerGet() {
		return playerGet;
	}

	public void setPlayerGet(int[] playerGet) {
		this.playerGet = playerGet;
	}

	public int[] getPayType() {
		return payType;
	}

	public void setPayType(int[] payType) {
		this.payType = payType;
	}

	public String[] getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String[] skillDesc) {
		this.skillDesc = skillDesc;
	}

	
}
