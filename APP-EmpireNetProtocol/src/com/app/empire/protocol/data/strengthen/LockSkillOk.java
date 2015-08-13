package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class LockSkillOk extends AbstractData {
    public LockSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkillOk, sessionId, serial);
    }

    public LockSkillOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkillOk);
    }
}
