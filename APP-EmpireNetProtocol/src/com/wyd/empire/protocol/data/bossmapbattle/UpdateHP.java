package com.wyd.empire.protocol.data.bossmapbattle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class UpdateHP extends AbstractData {
    private int   battleId;
    private int   playercount;  // 人数
    private int[] playerIds;
    private int[] hp;
    private int   guaicount;    // 怪数量
    private int[] guaiBattleIds; // 所有怪id
    private int[] guaiHp;       // 所有怪的当前血量
    private int   attackType;   // 类型(-2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id)

    public UpdateHP(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_UpdateHP, sessionId, serial);
    }

    public UpdateHP() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_UpdateHP);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
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

    public int getPlayercount() {
        return playercount;
    }

    public void setPlayercount(int playercount) {
        this.playercount = playercount;
    }

    public int getGuaicount() {
        return guaicount;
    }

    public void setGuaicount(int guaicount) {
        this.guaicount = guaicount;
    }

    public int[] getGuaiBattleIds() {
        return guaiBattleIds;
    }

    public void setGuaiBattleIds(int[] guaiBattleIds) {
        this.guaiBattleIds = guaiBattleIds;
    }

    public int[] getGuaiHp() {
        return guaiHp;
    }

    public void setGuaiHp(int[] guaiHp) {
        this.guaiHp = guaiHp;
    }

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }
}
