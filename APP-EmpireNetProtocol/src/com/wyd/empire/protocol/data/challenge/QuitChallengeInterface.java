package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuitChallengeInterface extends AbstractData {

	public QuitChallengeInterface(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitChallengeInterface, sessionId, serial);
	}

	public QuitChallengeInterface() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitChallengeInterface);
	}

}
