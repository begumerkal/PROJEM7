package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class SessionClosed extends AbstractData {
    private int session;

    public SessionClosed(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SessionClosed, sessionId, serial);
    }

    public SessionClosed() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SessionClosed);
    }

    public int getSession() {
        return this.session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}
