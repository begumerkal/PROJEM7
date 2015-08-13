package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UsingFace extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     faceId;
	public UsingFace(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UsingFace, sessionId, serial);
	}

	public UsingFace() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UsingFace);
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

	public int getFaceId() {
		return faceId;
	}

	public void setFaceId(int faceId) {
		this.faceId = faceId;
	}
}
