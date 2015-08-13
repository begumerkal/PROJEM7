package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetWedList extends AbstractData {

    public GetWedList(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetWedList, sessionId, serial);
    }

    public GetWedList() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetWedList);
    }

}
