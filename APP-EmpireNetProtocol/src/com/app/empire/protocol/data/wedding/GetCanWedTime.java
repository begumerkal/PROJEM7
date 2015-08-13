package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
