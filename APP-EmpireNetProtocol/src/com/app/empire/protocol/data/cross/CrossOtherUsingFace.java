package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossOtherUsingFace extends CrossDate {
    private int roomId;
    private int battleId;
    private int playerId;
    private int currentPlayerId;
    private int faceId;

    public CrossOtherUsingFace() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossOtherUsingFace);
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

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }
}
