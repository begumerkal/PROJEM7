package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GameReady extends AbstractData {
	private int      roomId;
	private int      oldSeat;
	private boolean  ready;
	public GameReady(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GameReady, sessionId, serial);
	}

	public GameReady() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GameReady);
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
