package com.app.empire.protocol.data.nearby;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class PlayerOffline extends AbstractData {
    private int myInfoId;

    public PlayerOffline(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_PlayerOffline, sessionId, serial);
    }

    public PlayerOffline() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_PlayerOffline);
    }

    public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }
}
