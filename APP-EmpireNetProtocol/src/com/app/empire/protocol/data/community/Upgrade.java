package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
