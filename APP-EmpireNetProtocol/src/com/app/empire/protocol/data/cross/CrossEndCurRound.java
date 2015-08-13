package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossEndCurRound extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;

	public CrossEndCurRound() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossEndCurRound);
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
}
