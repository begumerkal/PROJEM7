package com.wyd.empire.world.battle;

/**
 * 修炼vo
 * 
 * @author 陈杰
 *
 */
public class PracticeVo {
	private int mapLeve; // 修炼等级
	private String[] bonusAttribute; // 修炼显示的加成属性（服务端传key，客户端显示value）
	private int[] bonusValue; // 属性加成值索引
	private String[] bonusValueStr; // 属性加成值
	private int playerLeve; // 等级要求
	private int[] bonusIndex; // 属性索引
	private int exp; // 所需经验
	private int lowMedalExchangeExp; // 普通勋章兑换经验值
	private int seniorMedalExchangeExp; // 高级勋章兑换经验值
	private int dayConsumptionNumber; // 日消耗数

	public int getMapLeve() {
		return mapLeve;
	}

	public void setMapLeve(int mapLeve) {
		this.mapLeve = mapLeve;
	}

	public String[] getBonusAttribute() {
		return bonusAttribute;
	}

	public void setBonusAttribute(String[] bonusAttribute) {
		this.bonusAttribute = bonusAttribute;
	}

	public int[] getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int[] bonusValue) {
		this.bonusValue = bonusValue;
	}

	public int getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(int playerLeve) {
		this.playerLeve = playerLeve;
	}

	public int[] getBonusIndex() {
		return bonusIndex;
	}

	public void setBonusIndex(int[] bonusIndex) {
		this.bonusIndex = bonusIndex;
	}

	public String[] getBonusValueStr() {
		return bonusValueStr;
	}

	public void setBonusValueStr(String[] bonusValueStr) {
		this.bonusValueStr = bonusValueStr;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLowMedalExchangeExp() {
		return lowMedalExchangeExp;
	}

	public void setLowMedalExchangeExp(int lowMedalExchangeExp) {
		this.lowMedalExchangeExp = lowMedalExchangeExp;
	}

	public int getSeniorMedalExchangeExp() {
		return seniorMedalExchangeExp;
	}

	public void setSeniorMedalExchangeExp(int seniorMedalExchangeExp) {
		this.seniorMedalExchangeExp = seniorMedalExchangeExp;
	}

	public int getDayConsumptionNumber() {
		return dayConsumptionNumber;
	}

	public void setDayConsumptionNumber(int dayConsumptionNumber) {
		this.dayConsumptionNumber = dayConsumptionNumber;
	}

}
