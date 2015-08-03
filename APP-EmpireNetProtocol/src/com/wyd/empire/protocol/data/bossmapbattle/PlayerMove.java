package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PlayerMove extends AbstractData {
	private int     battleId;
	private int     playerOrGuai;
	private int     currentId;
	private int     movecount;
	private byte[]  movestep;
	private int     curPositionX;
	private int     curPositionY;
	public PlayerMove(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_PlayerMove, sessionId, serial);
	}

	public PlayerMove() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_PlayerMove);
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

	public int getMovecount() {
		return movecount;
	}

	public void setMovecount(int movecount) {
		this.movecount = movecount;
	}

	public byte[] getMovestep() {
		return movestep;
	}

	public void setMovestep(byte[] movestep) {
		this.movestep = movestep;
	}

	public int getCurPositionX() {
		return curPositionX;
	}

	public void setCurPositionX(int curPositionX) {
		this.curPositionX = curPositionX;
	}

	public int getCurPositionY() {
		return curPositionY;
	}

	public void setCurPositionY(int curPositionY) {
		this.curPositionY = curPositionY;
	}
}
