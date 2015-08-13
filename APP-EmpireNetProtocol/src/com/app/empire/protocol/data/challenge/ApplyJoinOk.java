package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ApplyJoinOk extends AbstractData {
	
	
    public ApplyJoinOk(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_ApplyJoinOk, sessionId, serial);
    }

    public ApplyJoinOk() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_ApplyJoinOk);
    }
}
