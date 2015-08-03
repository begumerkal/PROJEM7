package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class AIControlCommon extends AbstractData {
	private int     battleId;
	private int     idcount;
	private int[]   playerIds;
	private int[]   aiCtrlId;
	private int     guaiIdCount;
	private int[]   guaiBattleIds;
	private int[]   guaiAiCtrlId;

	public AIControlCommon(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_AIControlCommon, sessionId, serial);
	}

	public AIControlCommon() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_AIControlCommon);
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

	public int getGuaiIdCount() {
		return guaiIdCount;
	}

	public void setGuaiIdCount(int guaiIdCount) {
		this.guaiIdCount = guaiIdCount;
	}

	public int[] getGuaiBattleIds() {
		return guaiBattleIds;
	}

	public void setGuaiBattleIds(int[] guaiBattleIds) {
		this.guaiBattleIds = guaiBattleIds;
	}

	public int[] getGuaiAiCtrlId() {
		return guaiAiCtrlId;
	}

	public void setGuaiAiCtrlId(int[] guaiAiCtrlId) {
		this.guaiAiCtrlId = guaiAiCtrlId;
	}
}
