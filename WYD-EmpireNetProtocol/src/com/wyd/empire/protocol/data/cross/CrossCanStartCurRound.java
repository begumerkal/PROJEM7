package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossCanStartCurRound extends CrossDate {
    private int   roomId;
    private int   battleId;
    private int   wind;
    private int   currentPlayerId;
    private int   isCrit;
    private int[] attackRate;
    private int   isNewRound;
    private int[] battleRand;      // 游戏随机数

    public CrossCanStartCurRound() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossCanStartCurRound);
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

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getIsCrit() {
        return isCrit;
    }

    public void setIsCrit(int isCrit) {
        this.isCrit = isCrit;
    }

    public int[] getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(int[] attackRate) {
        this.attackRate = attackRate;
    }

    public int getIsNewRound() {
        return isNewRound;
    }

    public void setIsNewRound(int isNewRound) {
        this.isNewRound = isNewRound;
    }

    public int[] getBattleRand() {
        return battleRand;
    }

    public void setBattleRand(int[] battleRand) {
        this.battleRand = battleRand;
    }
}
