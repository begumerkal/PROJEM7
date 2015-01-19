package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class BackToRoom extends AbstractData {
	private int     roomId;
	public BackToRoom(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BackToRoom, sessionId, serial);
	}

	public BackToRoom() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BackToRoom);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
