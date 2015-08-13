package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RemoveNearbyFriendOk extends AbstractData {
	private int playerId;
	public RemoveNearbyFriendOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_RemoveNearbyFriendOk, sessionId, serial);
	}

	public RemoveNearbyFriendOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_RemoveNearbyFriendOk);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}
