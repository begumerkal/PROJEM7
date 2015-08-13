package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;

public class CrossChangeAngryValue extends CrossDate {
    private int     roomId;
	private int     battleId;
	private int     playerId;
	private int     angryValue;

	public CrossChangeAngryValue() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossChangeAngryValue);
	}

	public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public int getAngryValue() {
		return angryValue;
	}

	public void setAngryValue(int angryValue) {
		this.angryValue = angryValue;
	}
}
