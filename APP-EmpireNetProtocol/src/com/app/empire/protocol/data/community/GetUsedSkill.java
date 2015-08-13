package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetUsedSkill extends AbstractData {

    public GetUsedSkill(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetUsedSkill, sessionId, serial);
    }

    public GetUsedSkill() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetUsedSkill);
    }


}
