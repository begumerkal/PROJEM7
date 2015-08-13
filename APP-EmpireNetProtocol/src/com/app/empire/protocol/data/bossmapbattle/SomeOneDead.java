package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SomeOneDead extends AbstractData {
	private int     battleId;
	private int     deadPlayerCount;
	private int[]   playerIds;
	private int     deadGuaiCount;	//	死亡怪量
	private int[]   guaiBattleIds;	//	谁死了
	
	public SomeOneDead(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SomeOneDead, sessionId, serial);
	}

	public SomeOneDead() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SomeOneDead);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
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

	public int getDeadGuaiCount() {
		return deadGuaiCount;
	}

	public void setDeadGuaiCount(int deadGuaiCount) {
		this.deadGuaiCount = deadGuaiCount;
	}

	public int[] getGuaiBattleIds() {
		return guaiBattleIds;
	}

	public void setGuaiBattleIds(int[] guaiBattleIds) {
		this.guaiBattleIds = guaiBattleIds;
	}
	
}
