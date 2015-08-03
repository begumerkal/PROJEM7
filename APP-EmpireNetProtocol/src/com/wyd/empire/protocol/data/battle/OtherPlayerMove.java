package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherPlayerMove extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     movecount;
	private byte[]  movestep;
	private int     curPositionX;
	private int     curPositionY;
	public OtherPlayerMove(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherPlayerMove, sessionId, serial);
	}

	public OtherPlayerMove() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherPlayerMove);
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

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
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
