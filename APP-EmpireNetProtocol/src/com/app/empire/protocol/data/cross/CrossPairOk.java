package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossPairOk extends CrossDate {
    private int roomId;
    private int battleId;

    public CrossPairOk() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPairOk);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }
}
