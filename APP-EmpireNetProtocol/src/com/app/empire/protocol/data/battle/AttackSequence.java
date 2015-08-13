package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class AttackSequence extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     idcount;
	private int[]   playerIds;
	public AttackSequence(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_AttackSequence, sessionId, serial);
	}

	public AttackSequence() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_AttackSequence);
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

	public int getIdcount() {
		return idcount;
	}

	public void setIdcount(int idcount) {
		this.idcount = idcount;
	}

	public int[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(int[] playerIds) {
		this.playerIds = playerIds;
	}

}
