package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossBigSkill extends AbstractData {
	private int     battleId;
	private int     playerId;

	public CrossBigSkill() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossBigSkill);
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
