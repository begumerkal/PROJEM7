package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MosaicOk extends AbstractData {
    public MosaicOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MosaicOk, sessionId, serial);
    }

    public MosaicOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MosaicOk);
    }
}
