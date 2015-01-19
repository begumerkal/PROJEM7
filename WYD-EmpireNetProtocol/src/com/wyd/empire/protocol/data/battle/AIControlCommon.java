package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class AIControlCommon extends AbstractData {
	private int     battleId;
	private int     idcount;
	private int[]   playerIds;
	private int[]   aiCtrlId;
	public AIControlCommon(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_AIControlCommon, sessionId, serial);
	}

	public AIControlCommon() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_AIControlCommon);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
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

	public int[] getAiCtrlId() {
		return aiCtrlId;
	}

	public void setAiCtrlId(int[] aiCtrlId) {
		this.aiCtrlId = aiCtrlId;
	}
}
