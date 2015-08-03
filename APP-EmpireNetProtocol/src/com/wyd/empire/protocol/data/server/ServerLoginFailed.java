package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ServerLoginFailed extends AbstractData {
    private String reson;

    public String getReson() {
        return this.reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public ServerLoginFailed(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginFailed, sessionId, serial);
    }

    public ServerLoginFailed() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginFailed);
    }
}
