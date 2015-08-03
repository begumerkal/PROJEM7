package com.wyd.empire.protocol.data.exchange;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
