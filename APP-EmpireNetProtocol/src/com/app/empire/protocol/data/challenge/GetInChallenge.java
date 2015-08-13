package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetInChallenge extends AbstractData {
	
	
    public GetInChallenge(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInChallenge, sessionId, serial);
    }

    public GetInChallenge() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInChallenge);
    }
}
