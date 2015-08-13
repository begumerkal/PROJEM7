package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 玩家被冰冻
 * @author zgq
 *
 */
public class FrozenOver extends AbstractData {
    private int[] playerIds;
	public FrozenOver(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_FrozenOver, sessionId, serial);
	}

	public FrozenOver() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_FrozenOver);
	}

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }
}
