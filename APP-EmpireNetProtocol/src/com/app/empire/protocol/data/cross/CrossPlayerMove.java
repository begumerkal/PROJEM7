package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class CrossPlayerMove extends AbstractData {
    private int    battleId;
    private int    playerId;
    private int    movecount;
    private byte[] movestep;
    private int    curPositionX;
    private int    curPositionY;
    private int    movecountFloat;

    public CrossPlayerMove() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPlayerMove);
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

    public int getMovecount() {
        return movecount;
    }

    public void setMovecount(int movecount) {
        this.movecount = movecount;
    }

    public byte[] getMovestep() {
        return movestep;
    }

    public void setMovestep(byte[] movestep) {
        this.movestep = movestep;
    }

    public int getCurPositionX() {
        return curPositionX;
    }

    public void setCurPositionX(int curPositionX) {
        this.curPositionX = curPositionX;
    }

    public int getCurPositionY() {
        return curPositionY;
    }

    public void setCurPositionY(int curPositionY) {
        this.curPositionY = curPositionY;
    }

    public int getMovecountFloat() {
        return movecountFloat;
    }

    public void setMovecountFloat(int movecountFloat) {
        this.movecountFloat = movecountFloat;
    }
}
