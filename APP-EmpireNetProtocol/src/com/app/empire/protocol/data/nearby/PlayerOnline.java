package com.app.empire.protocol.data.nearby;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class PlayerOnline extends AbstractData {
    private int myInfoId;

    public PlayerOnline(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_PlayerOnline, sessionId, serial);
    }

    public PlayerOnline() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_PlayerOnline);
    }

    public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }
}
