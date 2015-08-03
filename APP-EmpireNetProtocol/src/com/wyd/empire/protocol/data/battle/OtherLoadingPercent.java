package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherLoadingPercent extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     percent;
	public OtherLoadingPercent(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherLoadingPercent, sessionId, serial);
	}

	public OtherLoadingPercent() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherLoadingPercent);
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

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

}
