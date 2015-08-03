package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class AuditPlayerOK extends AbstractData {
	
	
    public AuditPlayerOK(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_AuditPlayerOK, sessionId, serial);
    }

    public AuditPlayerOK() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_AuditPlayerOK);
    }
}
