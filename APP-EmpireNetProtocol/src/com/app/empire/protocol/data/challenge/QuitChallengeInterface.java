package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuitChallengeInterface extends AbstractData {

	public QuitChallengeInterface(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitChallengeInterface, sessionId, serial);
	}

	public QuitChallengeInterface() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitChallengeInterface);
	}

}
