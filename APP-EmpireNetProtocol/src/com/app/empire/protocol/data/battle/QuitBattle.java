package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuitBattle extends AbstractData {
	private int     battleId;
	private int     playerId;

	public QuitBattle(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_QuitBattle, sessionId, serial);
	}

	public QuitBattle() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_QuitBattle);
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
