package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTips extends AbstractData {
	
	public GetTips(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetTips, sessionId, serial);
	}

	public GetTips() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetTips);
	}
}
