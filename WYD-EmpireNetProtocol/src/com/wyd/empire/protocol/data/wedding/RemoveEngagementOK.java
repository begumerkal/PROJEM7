package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RemoveEngagementOK extends AbstractData {

    public RemoveEngagementOK(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagementOK, sessionId, serial);
    }

    public RemoveEngagementOK() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagementOK);
    }

}
