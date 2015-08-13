package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class BuySkillOk extends AbstractData {

    public BuySkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_BuySkillOk, sessionId, serial);
    }

    public BuySkillOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_BuySkillOk);
    }
}
