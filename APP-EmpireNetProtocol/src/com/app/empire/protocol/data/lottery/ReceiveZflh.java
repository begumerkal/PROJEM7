package com.app.empire.protocol.data.lottery;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ReceiveZflh extends AbstractData {
	private int id;//祝福礼盒ID
	public ReceiveZflh(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflh, sessionId, serial);
	}

	public ReceiveZflh() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflh);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
