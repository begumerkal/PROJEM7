package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetItemList extends AbstractData {
    public GetItemList(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetItemList, sessionId, serial);
    }

    public GetItemList() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetItemList);
    }
}
