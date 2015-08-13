package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;

public class CrossOtherPlayerMove extends CrossDate {
    private int   roomId;
	private int     battleId;
	private int     currentPlayerId;
	private int     movecount;
	private byte[]  movestep;
	private int     curPositionX;
	private int     curPositionY;

	public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public CrossOtherPlayerMove() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossOtherPlayerMove);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
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
