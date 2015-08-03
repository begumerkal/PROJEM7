package com.wyd.empire.protocol.data.bossmaproom;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CreateRoom extends AbstractData {
    private int    bossMapId;
    private String passWord;
    private int    difficulty; // 副本难度(0=普通,1=困难,2=地狱)

    public CreateRoom(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_CreateRoom, sessionId, serial);
    }

    public CreateRoom() {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_CreateRoom);
    }

    public int getBossMapId() {
        return bossMapId;
    }

    public void setBossMapId(int bossMapId) {
        this.bossMapId = bossMapId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
