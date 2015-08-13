package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuitRoomOk extends AbstractData {

	public QuitRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoomOk, sessionId, serial);
	}

	public QuitRoomOk() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoomOk);
	}
}
