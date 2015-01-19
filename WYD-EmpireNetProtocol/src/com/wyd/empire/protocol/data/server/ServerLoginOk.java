package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ServerLoginOk extends AbstractData {
    public ServerLoginOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginOk, sessionId, serial);
    }

    public ServerLoginOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginOk);
    }
}
