package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GameReadyOk extends AbstractData {
	private int      roomId;
	private int      oldSeat;
	private boolean  ready;
	public GameReadyOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GameReadyOk, sessionId, serial);
	}

	public GameReadyOk() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GameReadyOk);
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

	public boolean getReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
