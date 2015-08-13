package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossSomeOneDead extends CrossDate {
    private int     roomId;
    private int     battleId;
    private int     deadPlayerCount;
    private int[]   playerIds;
    private int     playerId;
    private int     shootSex;
    private int     shootCamp;
    private int[]   deadSex;
    private int[]   deadCamp;
    private int     killType;
    private boolean firstBlood;

    public CrossSomeOneDead() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossSomeOneDead);
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

    public int getDeadPlayerCount() {
        return deadPlayerCount;
    }

    public void setDeadPlayerCount(int deadPlayerCount) {
        this.deadPlayerCount = deadPlayerCount;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getShootSex() {
        return shootSex;
    }

    public void setShootSex(int shootSex) {
        this.shootSex = shootSex;
    }

    public int getShootCamp() {
        return shootCamp;
    }

    public void setShootCamp(int shootCamp) {
        this.shootCamp = shootCamp;
    }

    public int[] getDeadSex() {
        return deadSex;
    }

    public void setDeadSex(int[] deadSex) {
        this.deadSex = deadSex;
    }

    public int[] getDeadCamp() {
        return deadCamp;
    }

    public void setDeadCamp(int[] deadCamp) {
        this.deadCamp = deadCamp;
    }

    public int getKillType() {
        return killType;
    }

    public void setKillType(int killType) {
        this.killType = killType;
    }

    public boolean getFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }
}
