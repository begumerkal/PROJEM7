package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RewardOk extends AbstractData {
    
	public RewardOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RewardOk, sessionId, serial);
	}

	public RewardOk() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RewardOk);
	}
}
