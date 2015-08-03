package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class MosaicOk extends AbstractData {
    public MosaicOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MosaicOk, sessionId, serial);
    }

    public MosaicOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MosaicOk);
    }
}
