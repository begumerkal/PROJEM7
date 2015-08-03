package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PlayerQuit extends AbstractData {
	private int     oldSeat;
	
	public PlayerQuit(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_PlayerQuit, sessionId, serial);
	}

	public PlayerQuit() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_PlayerQuit);
	}

	public int getOldSeat() {
		return oldSeat;
	}

	public void setOldSeat(int oldSeat) {
		this.oldSeat = oldSeat;
	}
}
