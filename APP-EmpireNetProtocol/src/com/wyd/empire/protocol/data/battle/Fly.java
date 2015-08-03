package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Fly extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     speedx;	
	private int     speedy;
	private byte    leftRight;
	private int     isEquip;
	private int     startx;	
	private int     starty;
	private int     playerCount;
	private int[]   playerIds;	
	private int[]   curPositionX;	
	private int[]   curPositionY;
	
	public Fly(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Fly, sessionId, serial);
	}

	public Fly() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Fly);
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

	public int getIsEquip() {
		return isEquip;
	}

	public void setIsEquip(int isEquip) {
		this.isEquip = isEquip;
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
