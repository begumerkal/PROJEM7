package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Hurt extends AbstractData {
    private int       battleId;
    private int       playerId;
    private int       hurtcount;
    private int[]     playerIds;
    private int[]     hurtvalue;
    private int[]     distance;               // 受伤害玩家与爆炸点的距离
    private int[]     targetRandom;           // 被攻击目标武器技能触发使用随机数下标
    private int[]     attackerRandom;         // 攻击者武器技能触发使用随机数下标
    private int       hurtToBloodRate;        // 受伤多少伤害转换为血量的比率(放大1万倍)
    private int       attackType;             // 类型(-3:地图事件陨石攻击 -2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id)
    private int       hurtFloat;                // 伤害浮动值

	
	public Hurt(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Hurt, sessionId, serial);
	}

	public Hurt() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_Hurt);
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

    public int[] getDistance() {
        return distance;
    }

    public void setDistance(int[] distance) {
        this.distance = distance;
    }

    public int[] getTargetRandom() {
        return targetRandom;
    }

    public void setTargetRandom(int[] targetRandom) {
        this.targetRandom = targetRandom;
    }

    public int[] getAttackerRandom() {
        return attackerRandom;
    }

    public void setAttackerRandom(int[] attackerRandom) {
        this.attackerRandom = attackerRandom;
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
}
