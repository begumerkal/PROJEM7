package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class FinishLoading extends AbstractData {
	private int     battleId;
	private int     playerId;

	public FinishLoading(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_FinishLoading, sessionId, serial);
	}

	public FinishLoading() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_FinishLoading);
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
