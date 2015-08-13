package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyFriendList extends AbstractData {
	private int playerInfoId;

	public GetNearbyFriendList(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyFriendList, sessionId, serial);
	}

	public GetNearbyFriendList() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyFriendList);
	}

	public int getPlayerInfoId() {
		return playerInfoId;
	}

	public void setPlayerInfoId(int playerInfoId) {
		this.playerInfoId = playerInfoId;
	}
}
