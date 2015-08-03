package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherUsingFace extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     faceId;
	public OtherUsingFace(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherUsingFace, sessionId, serial);
	}

	public OtherUsingFace() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherUsingFace);
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
