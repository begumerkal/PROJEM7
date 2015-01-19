package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossUpdateHP extends CrossDate {
    private int   roomId;
    private int   battleId;
    private int   hurtcount;
    private int[] playerIds;
    private int[] hp;
    private int   attackType; // 类型(-2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id)

    public CrossUpdateHP() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossUpdateHP);
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
