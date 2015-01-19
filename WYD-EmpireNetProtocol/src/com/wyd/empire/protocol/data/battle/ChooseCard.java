package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChooseCard extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     cardPos;
	public ChooseCard(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ChooseCard, sessionId, serial);
	}

	public ChooseCard() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ChooseCard);
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

	public int getCardPos() {
		return cardPos;
	}

	public void setCardPos(int cardPos) {
		this.cardPos = cardPos;
	}
}
