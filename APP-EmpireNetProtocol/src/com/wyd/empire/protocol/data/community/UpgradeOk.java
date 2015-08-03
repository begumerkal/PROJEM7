package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
