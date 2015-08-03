package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class LegacyLogout extends AbstractData {
    private String name;
    private String key;

    public LegacyLogout(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogout, sessionId, serial);
    }

    public LegacyLogout() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogout);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
