package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossPass extends AbstractData {
	private int     battleId;
	private int     playerId;
	public CrossPass(int sessionId, int serial) {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPass, sessionId, serial);
	}

	public CrossPass() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPass);
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
}
