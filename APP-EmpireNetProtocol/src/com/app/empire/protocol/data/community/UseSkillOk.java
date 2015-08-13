package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class UseSkillOk extends AbstractData {

    public UseSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UseSkillOk, sessionId, serial);
    }

    public UseSkillOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UseSkillOk);
    }

}
