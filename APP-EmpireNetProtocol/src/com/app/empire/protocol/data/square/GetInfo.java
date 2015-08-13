package com.app.empire.protocol.data.square;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetInfo extends AbstractData {


    public GetInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetInfo, sessionId, serial);
    }

    public GetInfo() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetInfo);
    }

}
