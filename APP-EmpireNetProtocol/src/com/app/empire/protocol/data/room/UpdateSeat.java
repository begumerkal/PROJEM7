package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdateSeat extends AbstractData {
	private int     roomId;
	private int     oldSeat;
	private int     newSeat;
	public UpdateSeat(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_UpdateSeat, sessionId, serial);
	}

	public UpdateSeat() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_UpdateSeat);
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

	public int getNewSeat() {
		return newSeat;
	}

	public void setNewSeat(int newSeat) {
		this.newSeat = newSeat;
	}
}
