package com.wyd.empire.battle.bean;
import com.wyd.protocol.data.AbstractData;
import com.wyd.session.AcceptSession;
public class RandomRoom {
    private int           id;
    private boolean       ready = false;
    private int           count = 0;
    private int           averageFighting;
    private int           battleMode;
    private int           playerNum;
    private int           roomChannel;
    private String        version;
    private AcceptSession session;

    public RandomRoom(AcceptSession session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getAverageFighting() {
        return averageFighting;
    }

    public void setAverageFighting(int averageFighting) {
        this.averageFighting = averageFighting;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void sendData(AbstractData data) {
        if (null != session) session.send(data);
    }
    
    public String getServerId(){
        return session.getId();
    }
}
