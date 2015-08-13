package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;

public class CrossOtherLoadingPercent extends CrossDate {
    private int   roomId;
	private int     battleId;
	private int     currentPlayerId;
	private int     percent;

	public CrossOtherLoadingPercent() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossOtherLoadingPercent);
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

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

}
