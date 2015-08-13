package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class OtherUseFly extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	public OtherUseFly(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherUseFly, sessionId, serial);
	}

	public OtherUseFly() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherUseFly);
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
