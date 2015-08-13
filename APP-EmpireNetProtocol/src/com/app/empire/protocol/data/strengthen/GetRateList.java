package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRateList extends AbstractData {
    
    public GetRateList(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetRateList, sessionId, serial);
    }

    public GetRateList() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetRateList);
    }
}
