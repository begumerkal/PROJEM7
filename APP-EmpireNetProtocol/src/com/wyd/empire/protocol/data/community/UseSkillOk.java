package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
