package com.wyd.empire.protocol.data.lottery;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ReceiveReward extends AbstractData {
	
	public ReceiveReward(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveReward, sessionId, serial);
	}

	public ReceiveReward() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveReward);
	}

}
