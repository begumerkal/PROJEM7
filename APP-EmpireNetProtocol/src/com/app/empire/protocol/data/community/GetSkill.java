package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkill extends AbstractData {

    public GetSkill(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkill, sessionId, serial);
    }

    public GetSkill() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkill);
    }


}
