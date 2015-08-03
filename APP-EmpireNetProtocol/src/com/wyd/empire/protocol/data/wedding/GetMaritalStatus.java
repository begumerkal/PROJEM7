package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
