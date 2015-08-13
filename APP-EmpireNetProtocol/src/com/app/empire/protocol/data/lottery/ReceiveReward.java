package com.app.empire.protocol.data.lottery;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ReceiveReward extends AbstractData {
	
	public ReceiveReward(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveReward, sessionId, serial);
	}

	public ReceiveReward() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveReward);
	}

}
