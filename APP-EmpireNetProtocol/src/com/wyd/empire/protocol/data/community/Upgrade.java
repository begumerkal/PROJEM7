package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class Upgrade extends AbstractData {

    public Upgrade(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_Upgrade, sessionId, serial);
    }

    public Upgrade() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_Upgrade);
    }


}
