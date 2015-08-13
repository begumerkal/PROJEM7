package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ClearFailNumOk extends AbstractData {
	public ClearFailNumOk(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ClearFailNumOk, sessionId, serial);
	}

	public ClearFailNumOk() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ClearFailNumOk);
	}
}
