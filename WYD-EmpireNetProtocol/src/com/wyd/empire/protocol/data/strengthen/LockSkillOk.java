package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class LockSkillOk extends AbstractData {
    public LockSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkillOk, sessionId, serial);
    }

    public LockSkillOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkillOk);
    }
}
