package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CrossPositionsInMap extends AbstractData {
    private int   playerId;
    private int   battleId;
    private int   postionCount;
    private int[] postionX;
    private int[] postionY;

    public CrossPositionsInMap() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPositionsInMap);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getPostionCount() {
        return postionCount;
    }

    public void setPostionCount(int postionCount) {
        this.postionCount = postionCount;
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
}
