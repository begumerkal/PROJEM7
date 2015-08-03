package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
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
