package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
