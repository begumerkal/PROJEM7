package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class DeleteNearbyMailOk extends AbstractData {
	private int playerId;
	public DeleteNearbyMailOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_DeleteNearbyMailOk, sessionId, serial);
	}

	public DeleteNearbyMailOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_DeleteNearbyMailOk);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}
