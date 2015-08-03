package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossGotoBattle extends CrossDate {
    private int   roomId;
    private int   battleId;
    private int   wind;
    private int   currentPlayerId;
    private int   isCriticalHit;
    private int[] attackRate;
    private int[] battleRand;      // 游戏随机数

    public CrossGotoBattle() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossGotoBattle);
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

    public int getIsCriticalHit() {
        return isCriticalHit;
    }

    public void setIsCriticalHit(int isCriticalHit) {
        this.isCriticalHit = isCriticalHit;
    }

    public int[] getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(int[] attackRate) {
        this.attackRate = attackRate;
    }

    public int[] getBattleRand() {
        return battleRand;
    }

    public void setBattleRand(int[] battleRand) {
        this.battleRand = battleRand;
    }
}
