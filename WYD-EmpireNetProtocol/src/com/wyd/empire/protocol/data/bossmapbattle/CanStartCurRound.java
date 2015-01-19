package com.wyd.empire.protocol.data.bossmapbattle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CanStartCurRound extends AbstractData {
    private int   battleId;
    private int   wind;         // 风力（负数为坐风向，正数为右风向）
    private int   playerOrGuai; // 0:player 1:guai
    private int   currentId;    // Int 角色id(下回和操作的角色）
    private int[] attackRate;
    private int   isNewRound;
    private int[] battleRand;   // 游戏随机数
    private int   roundTimes;   // 当前游戏回合数

    public CanStartCurRound(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_CanStartCurRound, sessionId, serial);
    }

    public CanStartCurRound() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_CanStartCurRound);
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
   

    public int[] getBattleRand() {
        return battleRand;
    }

    public void setBattleRand(int[] battleRand) {
        this.battleRand = battleRand;
    }

    public int getRoundTimes() {
        return roundTimes;
    }

    public void setRoundTimes(int roundTimes) {
        this.roundTimes = roundTimes;
    }
}
