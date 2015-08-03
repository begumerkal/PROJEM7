package com.wyd.empire.protocol.data.battle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class UpdateHP extends AbstractData {
    private int   battleId;
    private int   playerId;
    private int   hurtcount;
    private int[] playerIds;
    private int[] hp;
    private int   attackType; // 类型(-2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id)

    public UpdateHP(int sessionId, int serial) {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UpdateHP, sessionId, serial);
    }

    public UpdateHP() {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UpdateHP);
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

    public int[] getHp() {
        return hp;
    }

    public void setHp(int[] hp) {
        this.hp = hp;
    }

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }
}
