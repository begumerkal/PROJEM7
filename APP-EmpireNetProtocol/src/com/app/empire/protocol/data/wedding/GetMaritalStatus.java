package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetMaritalStatus extends AbstractData {

    public GetMaritalStatus(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMaritalStatus, sessionId, serial);
    }

    public GetMaritalStatus() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMaritalStatus);
    }

}
