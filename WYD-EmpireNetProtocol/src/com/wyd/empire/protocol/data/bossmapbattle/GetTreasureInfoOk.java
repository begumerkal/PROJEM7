package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetTreasureInfoOk extends AbstractData {
	private int battleId;// 战斗id
	private int[] buffId;// 宝箱的id
	private int[] group; // 宝箱的分组
	private int[] buffType; // 宝箱的类型
	private String[] name;// 宝箱的名字
	private String[] icon;// 宝箱的icon
	private int[] effect1;// 宝箱的效果参数1
	private int[] effect2;// 宝箱的效果参数2
	private int[] turn;// 效果回合数
	private int[] probability;// 概率(10000)
	private int[] posX;// 出现的位置的X坐标
	private int[] posY;// 出现的位置的Y坐标

	public GetTreasureInfoOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GetTreasureInfoOk, sessionId, serial);
	}

	public GetTreasureInfoOk() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GetTreasureInfoOk);
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int[] getBuffId() {
		return buffId;
	}

	public void setBuffId(int[] buffId) {
		this.buffId = buffId;
	}

	public int[] getGroup() {
		return group;
	}

	public void setGroup(int[] group) {
		this.group = group;
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

	public int[] getEffect1() {
		return effect1;
	}

	public void setEffect1(int[] effect1) {
		this.effect1 = effect1;
	}

	public int[] getEffect2() {
		return effect2;
	}

	public void setEffect2(int[] effect2) {
		this.effect2 = effect2;
	}

	public int[] getTurn() {
		return turn;
	}

	public void setTurn(int[] turn) {
		this.turn = turn;
	}

	public int[] getProbability() {
		return probability;
	}

	public void setProbability(int[] probability) {
		this.probability = probability;
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

	public int[] getBuffType() {
		return buffType;
	}

	public void setBuffType(int[] buffType) {
		this.buffType = buffType;
	}

	public int getBattleId() {
		return battleId;
	}

}
