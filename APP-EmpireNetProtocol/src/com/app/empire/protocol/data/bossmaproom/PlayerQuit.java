package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PlayerQuit extends AbstractData {
	private int     oldSeat;
	
	public PlayerQuit(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_PlayerQuit, sessionId, serial);
	}

	public PlayerQuit() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_PlayerQuit);
	}

	public int getOldSeat() {
		return oldSeat;
	}

	public void setOldSeat(int oldSeat) {
		this.oldSeat = oldSeat;
	}
}
