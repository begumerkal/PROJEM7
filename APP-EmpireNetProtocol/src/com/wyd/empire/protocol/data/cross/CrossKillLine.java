package com.wyd.empire.protocol.data.cross;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

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
