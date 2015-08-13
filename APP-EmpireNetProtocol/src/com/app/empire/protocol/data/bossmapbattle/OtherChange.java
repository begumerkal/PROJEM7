package com.app.empire.protocol.data.bossmapbattle;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class OtherChange extends AbstractData {
    private int battleId; // 战斗id
    private int guaiBattleId;
    private int guaiOldId;
    private int guaiNewId;

    public OtherChange(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherChange, sessionId, serial);
    }

    public OtherChange() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherChange);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getGuaiBattleId() {
        return guaiBattleId;
    }

    public void setGuaiBattleId(int guaiBattleId) {
        this.guaiBattleId = guaiBattleId;
    }

    public int getGuaiOldId() {
        return guaiOldId;
    }

    public void setGuaiOldId(int guaiOldId) {
        this.guaiOldId = guaiOldId;
    }

    public int getGuaiNewId() {
        return guaiNewId;
    }

    public void setGuaiNewId(int guaiNewId) {
        this.guaiNewId = guaiNewId;
    }
}
