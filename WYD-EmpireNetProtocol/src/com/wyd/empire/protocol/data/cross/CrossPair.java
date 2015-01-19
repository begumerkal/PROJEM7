package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CrossPair extends AbstractData {
    private int    roomId;
    private int    battleMode;
    private int    playerNum;
    private int    roomChannel;
    private int    averageFighting;
    private String version;

    public CrossPair() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPair);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBattleMode() {
        return battleMode;
    }

    public void setBattleMode(int battleMode) {
        this.battleMode = battleMode;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getRoomChannel() {
        return roomChannel;
    }

    public void setRoomChannel(int roomChannel) {
        this.roomChannel = roomChannel;
    }

    public int getAverageFighting() {
        return averageFighting;
    }

    public void setAverageFighting(int averageFighting) {
        this.averageFighting = averageFighting;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
