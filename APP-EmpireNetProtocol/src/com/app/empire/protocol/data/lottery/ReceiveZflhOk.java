package com.app.empire.protocol.data.lottery;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ReceiveZflhOk extends AbstractData {
	public ReceiveZflhOk(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflhOk, sessionId, serial);
	}

	public ReceiveZflhOk() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflhOk);
	}

}
