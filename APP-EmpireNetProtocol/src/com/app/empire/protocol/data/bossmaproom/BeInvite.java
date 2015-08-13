package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class BeInvite extends AbstractData {
	private int roomId;
	private String playerName;

	public BeInvite(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_BeInvite, sessionId, serial);
	}

	public BeInvite() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_BeInvite);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
