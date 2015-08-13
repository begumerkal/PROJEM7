package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class UpgradeOk extends AbstractData {

    public UpgradeOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UpgradeOk, sessionId, serial);
    }

    public UpgradeOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UpgradeOk);
    }


}
