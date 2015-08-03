package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OhterChooseCard extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     cardPos;
	public OhterChooseCard(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OhterChooseCard, sessionId, serial);
	}

	public OhterChooseCard() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OhterChooseCard);
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

	public int getCardPos() {
		return cardPos;
	}

	public void setCardPos(int cardPos) {
		this.cardPos = cardPos;
	}
}
