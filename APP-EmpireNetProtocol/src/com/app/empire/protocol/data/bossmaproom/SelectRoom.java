package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SelectRoom extends AbstractData {
	private int roomId;
	public SelectRoom(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SelectRoom, sessionId, serial);
	}

	public SelectRoom() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SelectRoom);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
