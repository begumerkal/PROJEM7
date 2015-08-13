package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SomeOneDead extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     deadPlayerCount;
	private int[]   playerIds;
	private boolean firstBlood;
	
	public SomeOneDead(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_SomeOneDead, sessionId, serial);
	}

	public SomeOneDead() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_SomeOneDead);
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

	public int getDeadPlayerCount() {
		return deadPlayerCount;
	}

	public void setDeadPlayerCount(int deadPlayerCount) {
		this.deadPlayerCount = deadPlayerCount;
	}

	public int[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(int[] playerIds) {
		this.playerIds = playerIds;
	}

	public boolean getFirstBlood() {
		return firstBlood;
	}

	public void setFirstBlood(boolean firstBlood) {
		this.firstBlood = firstBlood;
	}
}
