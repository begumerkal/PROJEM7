package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class EndCurRound extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	public EndCurRound(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_EndCurRound, sessionId, serial);
	}

	public EndCurRound() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_EndCurRound);
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
