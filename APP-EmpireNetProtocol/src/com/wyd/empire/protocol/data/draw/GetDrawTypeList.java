package com.wyd.empire.protocol.data.draw;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetDrawTypeList extends AbstractData {
    public GetDrawTypeList(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetDrawTypeList, sessionId, serial);
    }

    public GetDrawTypeList() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetDrawTypeList);
    }
}
