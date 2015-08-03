package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetEventInfo extends AbstractData {
    private int battleId;
    public GetEventInfo(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetEventInfo, sessionId, serial);
	}

	public GetEventInfo() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetEventInfo);
	}
    public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	
}
