package com.wyd.empire.protocol.data.system;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class BattleShakeHands extends AbstractData {
    private int battleId;

    public BattleShakeHands(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_BattleShakeHands, sessionId, serial);
    }

    public BattleShakeHands() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_BattleShakeHands);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }
}
