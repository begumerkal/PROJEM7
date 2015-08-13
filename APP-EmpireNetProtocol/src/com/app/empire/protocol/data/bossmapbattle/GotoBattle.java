package com.app.empire.protocol.data.bossmapbattle;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GotoBattle extends AbstractData {
    private int   battleId;
    private int   wind;
    private int   playerOrGuai; // 0:player 1:guai
    private int   currentId;    // 角色id(下回和操作的角色）
    private int[] attackRate;
    private int[] battleRand;   // 游戏随机数
    private int   runTimes;     // 副本游戏回合数限制

    public GotoBattle(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GotoBattle, sessionId, serial);
    }

    public GotoBattle() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GotoBattle);
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
    
    public int getPlayerOrGuai() {
        return playerOrGuai;
    }

    public void setPlayerOrGuai(int playerOrGuai) {
        this.playerOrGuai = playerOrGuai;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
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

    public int getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(int runTimes) {
        this.runTimes = runTimes;
    }
}
