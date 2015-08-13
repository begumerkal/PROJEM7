package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UnLockSkillOk extends AbstractData {
    public UnLockSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_UnLockSkillOk, sessionId, serial);
    }

    public UnLockSkillOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_UnLockSkillOk);
    }
}
