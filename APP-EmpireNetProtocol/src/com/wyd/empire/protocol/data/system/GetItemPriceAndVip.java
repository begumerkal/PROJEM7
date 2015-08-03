package com.wyd.empire.protocol.data.system;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetItemPriceAndVip extends AbstractData {

    public GetItemPriceAndVip(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetItemPriceAndVip, sessionId, serial);
    }

    public GetItemPriceAndVip() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetItemPriceAndVip);
    }
}
