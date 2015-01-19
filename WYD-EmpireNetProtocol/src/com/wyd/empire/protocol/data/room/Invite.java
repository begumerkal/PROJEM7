package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Invite extends AbstractData {
	private int roomId;
	private int playerId;
	public Invite(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_Invite, sessionId, serial);
	}

	public Invite() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_Invite);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
}
