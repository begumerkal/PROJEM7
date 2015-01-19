package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Pass extends AbstractData {
	private int     battleId;
	private int     playerId;
	public Pass(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Pass, sessionId, serial);
	}

	public Pass() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Pass);
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
