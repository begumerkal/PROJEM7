package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossKillLine extends AbstractData {
	private int     playerId;

	public CrossKillLine() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossKillLine);
	}

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
