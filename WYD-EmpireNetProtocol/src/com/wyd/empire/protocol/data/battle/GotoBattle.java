package com.wyd.empire.protocol.data.battle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GotoBattle extends AbstractData {
    private int   battleId;
    private int   playerId;
    private int   wind;
    private int   currentPlayerId;
    private int[] attackRate;
    private int[] battleRand;      // 游戏随机数

    public GotoBattle(int sessionId, int serial) {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GotoBattle, sessionId, serial);
    }

    public GotoBattle() {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GotoBattle);
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

    public int[] getBattleRand() {
        return battleRand;
    }

    public void setBattleRand(int[] battleRand) {
        this.battleRand = battleRand;
    }
}
