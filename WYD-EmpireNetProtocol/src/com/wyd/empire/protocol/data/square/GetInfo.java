package com.wyd.empire.protocol.data.square;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetInfo extends AbstractData {


    public GetInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetInfo, sessionId, serial);
    }

    public GetInfo() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetInfo);
    }

}
