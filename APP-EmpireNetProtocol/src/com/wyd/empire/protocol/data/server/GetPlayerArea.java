package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
