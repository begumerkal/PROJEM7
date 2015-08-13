package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetPlayerArea extends AbstractData {
    private String udid;

    public GetPlayerArea(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_GetPlayerArea, sessionId, serial);
    }

    public GetPlayerArea() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_GetPlayerArea);
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
}
