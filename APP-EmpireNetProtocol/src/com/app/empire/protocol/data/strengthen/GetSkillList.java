package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetSkillList extends AbstractData {
    public GetSkillList(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetSkillList, sessionId, serial);
    }

    public GetSkillList() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetSkillList);
    }
}
