package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class OtherRewardOk extends AbstractData {
    private int playerId;
	public OtherRewardOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherRewardOk, sessionId, serial);
	}

	public OtherRewardOk() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherRewardOk);
	}

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
