package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuitRoom extends AbstractData {
	private int roomId;
	private int oldSeat;

	public QuitRoom(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuitRoom, sessionId, serial);
	}

	public QuitRoom() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuitRoom);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getOldSeat() {
		return oldSeat;
	}

	public void setOldSeat(int oldSeat) {
		this.oldSeat = oldSeat;
	}
}
