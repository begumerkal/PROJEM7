package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class BigSkill extends AbstractData {
	private int     battleId;
	private int     playerId;
	public BigSkill(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BigSkill, sessionId, serial);
	}

	public BigSkill() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BigSkill);
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
