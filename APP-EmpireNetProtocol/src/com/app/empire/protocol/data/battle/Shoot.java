package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Shoot extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     speedx;	
	private int     speedy;
	private byte    leftRight;
	private int     startx;	
	private int     starty;
	private int     playerCount;
	private int[]   playerIds;	
	private int[]   curPositionX;	
    private int[]   curPositionY;
    private int[]   attackerRandom; // 攻击者武器技能触发使用随机数下标
	public Shoot(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Shoot, sessionId, serial);
	}

	public Shoot() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Shoot);
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

    public int[] getAttackerRandom() {
        return attackerRandom;
    }

    public void setAttackerRandom(int[] attackerRandom) {
        this.attackerRandom = attackerRandom;
    }
}
