package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
