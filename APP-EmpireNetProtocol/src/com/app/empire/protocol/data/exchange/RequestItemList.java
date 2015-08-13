package com.app.empire.protocol.data.exchange;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RequestItemList extends AbstractData {

    public RequestItemList(int sessionId, int serial) {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestItemList, sessionId, serial);
    }

    public RequestItemList() {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestItemList);
    }

}
