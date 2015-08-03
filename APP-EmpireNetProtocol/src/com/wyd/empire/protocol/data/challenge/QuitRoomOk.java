package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuitRoomOk extends AbstractData {

	public QuitRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoomOk, sessionId, serial);
	}

	public QuitRoomOk() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoomOk);
	}
}
