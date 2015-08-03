package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChangeAngryValue extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     angryValue;
	public ChangeAngryValue(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ChangeAngryValue, sessionId, serial);
	}

	public ChangeAngryValue() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ChangeAngryValue);
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

	public int getAngryValue() {
		return angryValue;
	}

	public void setAngryValue(int angryValue) {
		this.angryValue = angryValue;
	}
}
