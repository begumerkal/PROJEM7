package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossPlayerReborn extends CrossDate {
    private int       roomId;
    private int       battleId;
    private int       playercount;
    private int[]     playerIds;
    private int[]     postionX;
    private int[]     postionY;
    private boolean[] robot;

    public CrossPlayerReborn() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPlayerReborn);
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

    public int getPlayercount() {
        return playercount;
    }

    public void setPlayercount(int playercount) {
        this.playercount = playercount;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int[] getPostionX() {
        return postionX;
    }

    public void setPostionX(int[] postionX) {
        this.postionX = postionX;
    }

    public int[] getPostionY() {
        return postionY;
    }

    public void setPostionY(int[] postionY) {
        this.postionY = postionY;
    }

    public boolean[] getRobot() {
        return robot;
    }

    public void setRobot(boolean[] robot) {
        this.robot = robot;
    }
}
