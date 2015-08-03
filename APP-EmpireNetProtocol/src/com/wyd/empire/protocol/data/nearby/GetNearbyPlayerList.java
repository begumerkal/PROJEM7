package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetNearbyPlayerList extends AbstractData {
	private int playerInfoId;

	public GetNearbyPlayerList(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyPlayerList, sessionId, serial);
	}

	public GetNearbyPlayerList() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyPlayerList);
	}

	public int getPlayerInfoId() {
		return playerInfoId;
	}

	public void setPlayerInfoId(int playerInfoId) {
		this.playerInfoId = playerInfoId;
	}
}
