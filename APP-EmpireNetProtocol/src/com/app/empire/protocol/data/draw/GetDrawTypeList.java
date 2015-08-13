package com.app.empire.protocol.data.draw;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetDrawTypeList extends AbstractData {
    public GetDrawTypeList(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetDrawTypeList, sessionId, serial);
    }

    public GetDrawTypeList() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetDrawTypeList);
    }
}
