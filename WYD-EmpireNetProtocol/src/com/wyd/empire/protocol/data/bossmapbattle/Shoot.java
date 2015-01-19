package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Shoot extends AbstractData {
	private int     battleId;
	private int     playerOrGuai;
	private int     currentId;
	private int     speedx;	
	private int     speedy;
	private byte    leftRight;
	private int     startx;	
	private int     starty;
	private int     playerCount;
	private int[]   playerIds;	
	private int[]   curPositionX;	
	private int[]   curPositionY;
	private int   	guaiCount;	//怪的数量
	private int[]   guaiBattleId;		//怪id
	private int[]   guaiCurPositionX;	//	没飞行前的x坐标
	private int[]   guaiCurPositionY;	//	没飞行前的y坐标
	
	public Shoot(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_Shoot, sessionId, serial);
	}

	public Shoot() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_Shoot);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPlayerOrGuai() {
		return playerOrGuai;
	}

	public void setPlayerOrGuai(int playerOrGuai) {
		this.playerOrGuai = playerOrGuai;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
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

	public int[] getGuaiCurPositionX() {
		return guaiCurPositionX;
	}

	public void setGuaiCurPositionX(int[] guaiCurPositionX) {
		this.guaiCurPositionX = guaiCurPositionX;
	}

	public int[] getGuaiCurPositionY() {
		return guaiCurPositionY;
	}

	public void setGuaiCurPositionY(int[] guaiCurPositionY) {
		this.guaiCurPositionY = guaiCurPositionY;
	}

	public int getSpeedx() {
		return speedx;
	}

	public void setSpeedx(int speedx) {
		this.speedx = speedx;
	}

	public int getSpeedy() {
		return speedy;
	}

	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}

	public byte getLeftRight() {
		return leftRight;
	}

	public void setLeftRight(byte leftRight) {
		this.leftRight = leftRight;
	}

	public int getStartx() {
		return startx;
	}

	public void setStartx(int startx) {
		this.startx = startx;
	}

	public int getStarty() {
		return starty;
	}

	public void setStarty(int starty) {
		this.starty = starty;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(int[] playerIds) {
		this.playerIds = playerIds;
	}

	public int[] getCurPositionX() {
		return curPositionX;
	}

	public void setCurPositionX(int[] curPositionX) {
		this.curPositionX = curPositionX;
	}

	public int[] getCurPositionY() {
		return curPositionY;
	}

	public void setCurPositionY(int[] curPositionY) {
		this.curPositionY = curPositionY;
	}
}
