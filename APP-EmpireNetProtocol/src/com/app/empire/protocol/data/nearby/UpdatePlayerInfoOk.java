package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdatePlayerInfoOk extends AbstractData {
	private int playerId;
    private int myInfoId;

	public UpdatePlayerInfoOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerInfoOk, sessionId, serial);
	}

	public UpdatePlayerInfoOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerInfoOk);
	}

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }
}
