package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherPass extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	public OtherPass(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherPass, sessionId, serial);
	}

	public OtherPass() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherPass);
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
