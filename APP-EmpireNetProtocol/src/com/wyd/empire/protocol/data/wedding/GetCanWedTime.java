package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCanWedTime extends AbstractData {

    public GetCanWedTime(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetCanWedTime, sessionId, serial);
    }

    public GetCanWedTime() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetCanWedTime);
    }

}
