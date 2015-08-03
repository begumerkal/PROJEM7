package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
