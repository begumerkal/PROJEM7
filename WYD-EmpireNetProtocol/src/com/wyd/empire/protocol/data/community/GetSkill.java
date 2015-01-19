package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
