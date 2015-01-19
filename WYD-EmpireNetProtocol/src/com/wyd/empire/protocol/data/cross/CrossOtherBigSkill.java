package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossOtherBigSkill extends CrossDate {
    private int roomId;
    private int battleId;
    private int currentPlayerId;

    public CrossOtherBigSkill() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossOtherBigSkill);
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

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
