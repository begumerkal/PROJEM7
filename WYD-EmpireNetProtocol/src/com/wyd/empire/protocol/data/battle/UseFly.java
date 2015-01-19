package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UseFly extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;

	public UseFly(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UseFly, sessionId, serial);
	}

	public UseFly() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UseFly);
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
