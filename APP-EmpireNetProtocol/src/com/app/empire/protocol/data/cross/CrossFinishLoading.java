package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossFinishLoading extends AbstractData {
	private int     battleId;
	private int     playerId;
	
	public CrossFinishLoading() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossFinishLoading);
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