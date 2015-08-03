package com.wyd.empire.protocol.data.bossmaproom;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class UpdateRoom extends AbstractData {
    private int    roomId;
    private int    playerNumMode;
    private String passWord;
    private int    mapId;
    private int    wnersId;
    private int    difficulty;   // 副本难度(0=普通,1=困难,2=地狱)

    public UpdateRoom(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_UpdateRoom, sessionId, serial);
    }

    public UpdateRoom() {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_UpdateRoom);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getPlayerNumMode() {
        return playerNumMode;
    }

    public void setPlayerNumMode(int playerNumMode) {
        this.playerNumMode = playerNumMode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getWnersId() {
        return wnersId;
    }

    public void setWnersId(int wnersId) {
        this.wnersId = wnersId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
