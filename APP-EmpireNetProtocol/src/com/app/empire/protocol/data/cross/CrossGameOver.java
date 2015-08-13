package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossGameOver extends CrossDate {
    private int       roomId;
    private int       battleId;
    private int       firstHurtPlayerId;
    private int       winCamp;
    private int       times0;
    private int       times1;
    private int       round;
    private int       playerCount;
    private int       playerNumMode;
    private int[]     playerIds;
    private int[]     shootRate;
    private int[]     totalHurt;
    private int[]     killCount;
    private int[]     beKilledCount;
    private int[]     huntTimes;
    private int[]     hp;
    private int[]     pLevel;
    private int[]     actionTimes;
    private int[]     beKillRound;
    private boolean[] isEnforceQuit;
    private boolean[] isLost;
    private boolean[] isSuicide;
    private boolean[] isWin;

    public CrossGameOver() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossGameOver);
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

    public int getFirstHurtPlayerId() {
        return firstHurtPlayerId;
    }

    public void setFirstHurtPlayerId(int firstHurtPlayerId) {
        this.firstHurtPlayerId = firstHurtPlayerId;
    }

    public int getWinCamp() {
        return winCamp;
    }

    public void setWinCamp(int winCamp) {
        this.winCamp = winCamp;
    }

    public int getTimes0() {
        return times0;
    }

    public void setTimes0(int times0) {
        this.times0 = times0;
    }

    public int getTimes1() {
        return times1;
    }

    public void setTimes1(int times1) {
        this.times1 = times1;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerNumMode() {
        return playerNumMode;
    }

    public void setPlayerNumMode(int playerNumMode) {
        this.playerNumMode = playerNumMode;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int[] getShootRate() {
        return shootRate;
    }

    public void setShootRate(int[] shootRate) {
        this.shootRate = shootRate;
    }

    public int[] getTotalHurt() {
        return totalHurt;
    }

    public void setTotalHurt(int[] totalHurt) {
        this.totalHurt = totalHurt;
    }

    public int[] getKillCount() {
        return killCount;
    }

    public void setKillCount(int[] killCount) {
        this.killCount = killCount;
    }

    public int[] getBeKilledCount() {
        return beKilledCount;
    }

    public void setBeKilledCount(int[] beKilledCount) {
        this.beKilledCount = beKilledCount;
    }

    public int[] getHuntTimes() {
        return huntTimes;
    }

    public void setHuntTimes(int[] huntTimes) {
        this.huntTimes = huntTimes;
    }

    public int[] getHp() {
        return hp;
    }

    public void setHp(int[] hp) {
        this.hp = hp;
    }

    public int[] getpLevel() {
        return pLevel;
    }

    public void setpLevel(int[] pLevel) {
        this.pLevel = pLevel;
    }

    public int[] getActionTimes() {
        return actionTimes;
    }

    public void setActionTimes(int[] actionTimes) {
        this.actionTimes = actionTimes;
    }

    public int[] getBeKillRound() {
        return beKillRound;
    }

    public void setBeKillRound(int[] beKillRound) {
        this.beKillRound = beKillRound;
    }

    public boolean[] getIsEnforceQuit() {
        return isEnforceQuit;
    }

    public void setIsEnforceQuit(boolean[] isEnforceQuit) {
        this.isEnforceQuit = isEnforceQuit;
    }

    public boolean[] getIsLost() {
        return isLost;
    }

    public void setIsLost(boolean[] isLost) {
        this.isLost = isLost;
    }

    public boolean[] getIsSuicide() {
        return isSuicide;
    }

    public void setIsSuicide(boolean[] isSuicide) {
        this.isSuicide = isSuicide;
    }

    public boolean[] getIsWin() {
        return isWin;
    }

    public void setIsWin(boolean[] isWin) {
        this.isWin = isWin;
    }
}
