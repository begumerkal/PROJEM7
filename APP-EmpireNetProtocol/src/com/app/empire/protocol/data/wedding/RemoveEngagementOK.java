package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
