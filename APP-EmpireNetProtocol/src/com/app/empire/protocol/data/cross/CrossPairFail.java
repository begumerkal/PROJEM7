package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossPairFail extends CrossDate {
    protected int roomId;

    public CrossPairFail() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPairFail);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
