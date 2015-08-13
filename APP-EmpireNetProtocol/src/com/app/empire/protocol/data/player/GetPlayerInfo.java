package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetPlayerInfo extends AbstractData {
	private int playerId;//玩家ID
	public GetPlayerInfo(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfo, sessionId, serial);
	}

	public GetPlayerInfo() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfo);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}
