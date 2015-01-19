package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 玩家被冰冻
 * @author zgq
 *
 */
public class BeFrozen extends AbstractData {
    private int battleId;
    private int[] playerIds;
	public BeFrozen(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BeFrozen, sessionId, serial);
	}

	public BeFrozen() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_BeFrozen);
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
}
