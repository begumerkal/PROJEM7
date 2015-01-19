package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GameReadyOk extends AbstractData {
	private int      oldSeat;
	private boolean  ready;
	public GameReadyOk(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GameReadyOk, sessionId, serial);
	}

	public GameReadyOk() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GameReadyOk);
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
