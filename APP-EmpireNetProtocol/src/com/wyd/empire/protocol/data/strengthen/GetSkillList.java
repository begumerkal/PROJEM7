package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetSkillList extends AbstractData {
    public GetSkillList(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetSkillList, sessionId, serial);
    }

    public GetSkillList() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetSkillList);
    }
}
