package com.wyd.empire.protocol.data.system;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class TopHands extends AbstractData {

    public TopHands(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_TopHands, sessionId, serial);
    }

    public TopHands() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_TopHands);
    }
}
