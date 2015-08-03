package com.wyd.empire.protocol.data.cross;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CrossAIControlCommon extends AbstractData {
	private int     battleId;
    private int     playerId;
	private int     idcount;
	private int[]   playerIds;
	private int[]   aiCtrlId;

	public CrossAIControlCommon() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossAIControlCommon);
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

	public int[] getAiCtrlId() {
		return aiCtrlId;
	}

	public void setAiCtrlId(int[] aiCtrlId) {
		this.aiCtrlId = aiCtrlId;
	}
}
