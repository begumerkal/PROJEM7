package com.wyd.empire.world.battle;

public class StarSoulVo {
	private int mapLeve; // 星魂等级
	private String[] bonusAttribute; // 星点显示的加成属性（服务端传key，客户端显示value）
	private int[] bonusValue; // 属性加成值
	private String[] bonusValueStr; // 属性加成值
	private String[] consumptionGolds; // 消耗金币数
	private String[] consumptionDebris; // 消耗星魂碎片数
	private int playerLeve; // 等级要求
	private int[] coordinateX; // X坐标
	private int[] coordinateY; // Y坐标
	private int[] bonusIndex; // 属性索引

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

	public String[] getConsumptionGolds() {
		return consumptionGolds;
	}

	public void setConsumptionGolds(String[] consumptionGolds) {
		this.consumptionGolds = consumptionGolds;
	}

	public String[] getConsumptionDebris() {
		return consumptionDebris;
	}

	public void setConsumptionDebris(String[] consumptionDebris) {
		this.consumptionDebris = consumptionDebris;
	}

	public int getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(int playerLeve) {
		this.playerLeve = playerLeve;
	}

	public int[] getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int[] coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int[] getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int[] coordinateY) {
		this.coordinateY = coordinateY;
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

}
