package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ClearFailNum extends AbstractData {
	public ClearFailNum(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ClearFailNum, sessionId, serial);
	}

	public ClearFailNum() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_ClearFailNum);
	}
}
