package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossAttackSequence extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     idcount;
	private int[]   playerIds;
	private int[]   isCriticalHit;

	public CrossAttackSequence() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossAttackSequence);
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

	public int[] getIsCriticalHit() {
		return isCriticalHit;
	}

	public void setIsCriticalHit(int[] isCriticalHit) {
		this.isCriticalHit = isCriticalHit;
	}
}
