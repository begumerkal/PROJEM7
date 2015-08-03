package com.wyd.empire.protocol.data.bossmapbattle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class Hurt extends AbstractData {
    private int     battleId;
    private int     playerOrGuai;   // 0:player 1:guai
    private int     currentId;      // 角色id
    private int     hurtcount;
    private int[]   playerIds;
    private int[]   hurtvalue;
    private int     guaihurtcount;  // 受伤害的人数
    private int[]   guaiBattleIds;  // 对应受伤害的序列
    private int[]   guaiHurtValue;  // 受伤害值
    private int     hurtToBloodRate; // 受伤多少伤害转换为血量的比率(放大1万倍)
    private int     attackType;     // 类型(0:普通伤害，1:普通技能攻击，2:平分伤害类型技能 )
    private int     hurtFloat;      // 伤害浮动值
    private int     attackIndex;     // 技能伤害值下标
    private boolean bossBeFrozen;    // 本回合世界BOSS是否被冰冻

    public Hurt(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_Hurt, sessionId, serial);
    }

    public Hurt() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_Hurt);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
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

    public int getGuaihurtcount() {
        return guaihurtcount;
    }

    public void setGuaihurtcount(int guaihurtcount) {
        this.guaihurtcount = guaihurtcount;
    }

    public int[] getGuaiBattleIds() {
        return guaiBattleIds;
    }

    public void setGuaiBattleIds(int[] guaiBattleIds) {
        this.guaiBattleIds = guaiBattleIds;
    }

    public int[] getGuaiHurtValue() {
        return guaiHurtValue;
    }

    public void setGuaiHurtValue(int[] guaiHurtValue) {
        this.guaiHurtValue = guaiHurtValue;
    }

    public int getHurtcount() {
        return hurtcount;
    }

    public void setHurtcount(int hurtcount) {
        this.hurtcount = hurtcount;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int[] getHurtvalue() {
        return hurtvalue;
    }

    public void setHurtvalue(int[] hurtvalue) {
        this.hurtvalue = hurtvalue;
    }

    public int getHurtToBloodRate() {
        return hurtToBloodRate;
    }

    public void setHurtToBloodRate(int hurtToBloodRate) {
        this.hurtToBloodRate = hurtToBloodRate;
    }

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }

    public int getHurtFloat() {
        return hurtFloat;
    }

    public void setHurtFloat(int hurtFloat) {
        this.hurtFloat = hurtFloat;
    }

    public int getAttackIndex() {
        return attackIndex;
    }

    public void setAttackIndex(int attackIndex) {
        this.attackIndex = attackIndex;
    }

    public boolean getBossBeFrozen() {
        return bossBeFrozen;
    }

    public void setBossBeFrozen(boolean bossBeFrozen) {
        this.bossBeFrozen = bossBeFrozen;
    }
}
