package com.wyd.empire.protocol.data.battle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CanStartCurRound extends AbstractData {
    private int     battleId;
    private int     playerId;
    private int     wind;
    private int     currentPlayerId;
    private int[]   attackRate;
    private int     isNewRound;
    private int[]   battleRand;          // 游戏随机数

    public CanStartCurRound(int sessionId, int serial) {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_CanStartCurRound, sessionId, serial);
    }

    public CanStartCurRound() {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_CanStartCurRound);
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
