package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class StartNewTimer extends AbstractData {
	private int     battleId;
	private int     playerId;
	public StartNewTimer(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_StartNewTimer, sessionId, serial);
	}

	public StartNewTimer() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_StartNewTimer);
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
}
