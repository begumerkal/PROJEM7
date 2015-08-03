package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ShutDown extends AbstractData {
    public ShutDown(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ShutDown, sessionId, serial);
    }

    public ShutDown() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ShutDown);
    }
}
