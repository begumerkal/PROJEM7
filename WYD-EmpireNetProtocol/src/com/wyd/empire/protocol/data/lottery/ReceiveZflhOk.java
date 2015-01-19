package com.wyd.empire.protocol.data.lottery;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ReceiveZflhOk extends AbstractData {
	public ReceiveZflhOk(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflhOk, sessionId, serial);
	}

	public ReceiveZflhOk() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflhOk);
	}

}
